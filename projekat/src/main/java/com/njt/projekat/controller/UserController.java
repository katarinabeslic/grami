package com.njt.projekat.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import com.njt.projekat.entity.*;
import com.njt.projekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.njt.projekat.service.impl.UserSecurityService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CardInformationService cardInformationService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResults, RedirectAttributes redirectAttributes, Model model) {
		boolean invalidFields = false;
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		if (bindingResults.hasErrors()) {
			return "redirect:/login";
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			redirectAttributes.addFlashAttribute("usernameExists", true);
			invalidFields = true;
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			redirectAttributes.addFlashAttribute("emailExists", true);
			invalidFields = true;
		}
		if (invalidFields) {
			return "redirect:/login";
		}
		
		user = userService.createUser(user, Arrays.asList("ROLE_CUSTOMER"));
		userSecurityService.authenticateUser(user.getUsername());
		model.addAttribute("user", user);
		
		return "redirect:/my-profile";
	}

	@GetMapping("/success")
	public String loginPageRedirect(Model model, Authentication auth) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return "redirect:admin";
		} else {
			return "redirect:";
		}
	}

	@GetMapping("/my-profile")
	public String showUserProfile(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "my-profile";
	}
	
	@GetMapping("/my-address")
	public String showUserAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "my-address";
	}
	
	@GetMapping("/payment-method")
	public String showUserPaymentInfo(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "my-payment";
	}
	
	@PostMapping("/update-user")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam("newPassword") String newPassword, Model model, Principal principal, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		User currentUser = userService.findByUsername(principal.getName());
		if (newPassword.equals("no")) {
			user.setPassword(currentUser.getPassword());
			userService.save(user);
			return "redirect:" + referer;
		}
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
			if (bCryptPasswordEncoder.matches(user.getPassword(), currentUser.getPassword())) {
				user.setPassword(bCryptPasswordEncoder.encode(newPassword));
				userService.save(user);
			} else {
				model.addAttribute("incorrectPassword", true);
				return "redirect:" + referer;
			}
		}
		return "redirect:" + referer;
	}
	
	@PostMapping("/update-address")
	public String updateAddress(@ModelAttribute("address") Address address, Principal principal, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		User user = userService.findByUsername(principal.getName());
		Address existingAddress = addressService.findByUser(user);
		if (existingAddress != null) {
			address.setId(existingAddress.getId());
		}
		address.setUser(user);
		addressService.save(address);
		return "redirect:" + referer;
	}

	@PostMapping("/update-payment")
	public String updatePayment(@ModelAttribute("cardInformation") CardInformation cardInformation, Principal principal, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		User user = userService.findByUsername(principal.getName());
		CardInformation existingCardInformation = cardInformationService.findByUser(user);
		if (existingCardInformation != null) {
			cardInformation.setId(existingCardInformation.getId());
		}
		cardInformation.setUser(user);
		cardInformationService.save(cardInformation);
		return "redirect:" + referer;
	}
	
	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<CartItem> shoppingCart = shoppingCartService.findAllByUserAndOrderIsNull(user);
		if (shoppingCart.isEmpty()) {
			return "redirect:/shop";
		}
		return "shopping-cart";
	}

	@GetMapping("/my-orders")
	public String showOrders(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<Order> orders = orderService.findByUser(user);
		model.addAttribute("user", user);
		model.addAttribute("orders",orders);
		return "my-orders";
	}

	@GetMapping("/order-detail")
	public String showOrderDetail(@RequestParam("id") int id, Model model) {
		Order order = orderService.findById(id);
		model.addAttribute("order", order);
		return "order-detail";
	}
}
