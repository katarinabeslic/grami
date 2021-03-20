package com.njt.projekat.controller;

import java.security.Principal;
import java.util.Arrays;

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

import com.njt.projekat.entity.Address;
import com.njt.projekat.entity.CardInformation;
import com.njt.projekat.entity.User;
import com.njt.projekat.service.AddressService;
import com.njt.projekat.service.CardInformationService;
import com.njt.projekat.service.UserService;
import com.njt.projekat.service.impl.UserSecurityService;

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
		
		return "redirect:/user";
	}

	@GetMapping("/success")
	public String loginPageRedirect(Model model, Authentication auth) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return "redirect:admin";
		} else {
			return "redirect:";
		}
	}

	@GetMapping("/user")
	public String showUserProfile(Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "user-profile";
	}
	
	@GetMapping("/my-address")
	public String showUserAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Address address = addressService.findByUser(user);
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		return "my-address";
	}
	
	@GetMapping("/payment-method")
	public String showUserPaymentInfo(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		CardInformation cardInformation = cardInformationService.findByUser(user);
		model.addAttribute("user", user);
		model.addAttribute("cardInformation", cardInformation);
		return "my-payment";
	}
	
	@PostMapping("/update-user")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam("newPassword") String newPassword, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		if (newPassword != null && !newPassword.isEmpty()) {
			if (bCryptPasswordEncoder.matches(user.getPassword(), currentUser.getPassword())) {
				user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				return "user-profile";
			}
		}
		user = userService.save(user);
		model.addAttribute("user", user);
		return "redirect:/user";
	}
	
	@PostMapping("/update-address")
	public String updateAddress(@ModelAttribute("address") Address address, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		address.setUser(currentUser);
		address = addressService.save(address);
		model.addAttribute("address", address);
		return "redirect:/my-address";
	}
	
	@PostMapping("/update-payment")
	public String updatePayment(@ModelAttribute("cardInformation") CardInformation cardInformation, Model model, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		cardInformation.setUser(currentUser);
		cardInformation = cardInformationService.save(cardInformation);
		System.out.println(cardInformation);
		model.addAttribute("cardInformation", cardInformation);
		return "redirect:/payment-method";
	}
	
	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		//User currentUser = userService.findByUsername(principal.getName());
		
		return "shopping-cart";
	}
}
