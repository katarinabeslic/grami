package com.njt.projekat.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.njt.projekat.entity.*;
import com.njt.projekat.service.*;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njt.projekat.form.VinylFilterForm;
import com.njt.projekat.util.SortFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.Card;

@Controller
public class ShopController {

    @Autowired
    private UserService userService;
    @Autowired
    private VinylService vinylService;
    @Autowired
    private FormatService formatService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CardInformationService cardInformationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaypalService paypalService;

    public static final String SUCCESS_URL = "paypal-successful";
    public static final String CANCEL_URL = "cancel";

    public static Order order;

    @GetMapping("/shop")
    public String showUserShop(@ModelAttribute("filters") VinylFilterForm filters, Model model) {
        System.out.println("FILTERS: " + filters);
        if (filters.getFormat() == "") {
            filters.setFormat(null);
        }
        if (filters.getItemsPerPage() == null) {
            filters.setItemsPerPage(9);
        }
        Integer page = filters.getPage();
        int pageNumber = (page == null || page <= 0) ? 0 : page - 1;
        SortFilter sortFilter = new SortFilter(filters.getSort());
        Page<Vinyl> pageResult = vinylService.findVinylsByCriteria(
                PageRequest.of(pageNumber, filters.getItemsPerPage(), sortFilter.getSortType()), filters.getFormat(), filters.getGenres(), filters.getSearch());
        String classActiveSort = "active" + filters.getSort();
        classActiveSort = classActiveSort.replaceAll("\\s+", "");
        classActiveSort = classActiveSort.replaceAll("&", "");

        model.addAttribute(classActiveSort, true);
        model.addAttribute("vinyls", pageResult.getContent());
        model.addAttribute("formats", formatService.findAll());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("totalItems", pageResult.getTotalElements());
        model.addAttribute("itemsPerPage", filters.getItemsPerPage());
        model.addAttribute("quantity", 1);
        return "shop";
    }

    @GetMapping("/shop/vinyl")
    public String showUserVinyl(@RequestParam("id") int id, Model model) {
        model.addAttribute("vinyl", vinylService.findById(id));
        model.addAttribute("quantity", 1);
        return "vinyl-detail";
    }

    @GetMapping("/shop/add-to-cart")
    public String addItemToCart(@RequestParam("id") Vinyl vinyl, @RequestParam(value = "quantity", required = false) String quantity, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");
        int qty = 0;
        if (quantity == null) {
            qty = 1;
        } else {
            qty = Integer.valueOf(quantity);
        }
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        CartItem existingItem = shoppingCartService.findByUserAndVinylAndOrderIsNull(user, vinyl);
        if (qty > vinyl.getStock()) {
            redirectAttributes.addFlashAttribute("notEnoughStock", true);
            return "redirect:" + referer;
        }
        if (existingItem == null) {
            CartItem cartItem = new CartItem(vinyl, qty, user);
            shoppingCartService.save(cartItem);
        } else {
            existingItem.increaseQuantity(qty);
            shoppingCartService.save(existingItem);
        }
        return "redirect:" + referer;
    }

    @GetMapping("/checkout")
    public String showUserCheckout(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(principal.getName());
        List<CartItem> shoppingCart = shoppingCartService.findAllByUserAndOrderIsNull(user);
        if (shoppingCart.isEmpty()) {
            redirectAttributes.addFlashAttribute("cartEmpty", true);
            return "redirect:/shop";
        }
        model.addAttribute("user", user);
        model.addAttribute("shoppingCart", shoppingCart);
        return "checkout";
    }

    @PostMapping("/card-payment")
    public String processCardPayment(@ModelAttribute("shoppingCartTotal") double cartTotal, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        Address address = addressService.findByUser(user);
        CardInformation cardInformation = cardInformationService.findByUser(user);
        List<CartItem> shoppingCart = shoppingCartService.findAllByUserAndOrderIsNull(user);
        if (!shoppingCart.isEmpty()) {
            Order order = orderService.save(cartTotal, user, address, cardInformation, shoppingCart);
            redirectAttributes.addFlashAttribute("order", order);
            return "redirect:/order-successful";
        }
        return "redirect:/checkout";
    }

    @GetMapping("/order-successful")
    private String showUserSuccessfulOrder(Model model) {
        Order order = (Order) model.asMap().get("order");
        if (order != null) {
            model.addAttribute("order", order);
            return "order-successful";
        }
        return "redirect:error";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "checkout";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, Principal principal, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        User user = userService.findByUsername(principal.getName());
        Address address = addressService.findByUser(user);
        try {
            Payment payment = paypalService.executePayment(paymentId, "FSPKT3UUKJYGJ");
            if (payment.getState().equals("approved")) {
                List<CartItem> cartItems = shoppingCartService.findAllByUserAndOrderIsNull(user);
                order.setOrderStatus("Received");
                order.setCartItems(cartItems);
                order.setUser(user);
                order.setAddress(address);
                order.setDateAndTime(Timestamp.valueOf(LocalDateTime.now()));
                order = orderService.save(order);
                for (CartItem cartItem: cartItems) {
                    Vinyl vinyl = cartItem.getVinyl();
                    vinyl.decreaseStock(cartItem.getQuantity());
                    vinylService.save(vinyl);
                    cartItem.setOrder(order);
                    shoppingCartService.save(cartItem);
                }
                redirectAttributes.addFlashAttribute("order", order);
                return "redirect:order-successful";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
