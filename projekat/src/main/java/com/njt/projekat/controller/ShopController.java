package com.njt.projekat.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import com.njt.projekat.entity.*;
import com.njt.projekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
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
    public String showUserCheckout(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<CartItem> shoppingCart = shoppingCartService.findAllByUserAndOrderIsNull(user);
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
        return "redirect:/";
    }

}
