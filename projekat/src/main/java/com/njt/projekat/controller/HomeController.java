package com.njt.projekat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class HomeController {

	@GetMapping("/error")
	public String showErrorPage() {
		return "error";
	}
	
	@GetMapping("/")
	public String showIndexPage(Model model) {
		return "index";
	}
	
	@GetMapping("/admin")
	public String showAdminPage() {
		return "redirect:/admin/catalogue";
	}
	
	@GetMapping("/contact-faq")
	public String showContactFaq() {
		return "contact-faq";
	}
	
	@GetMapping("/about-us")
	public String showAboutUs() {
		return "about-us";
	}
	
}
