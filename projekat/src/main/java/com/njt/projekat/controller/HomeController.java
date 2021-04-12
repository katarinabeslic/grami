package com.njt.projekat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.njt.projekat.entity.User;


@Controller
public class HomeController {

	@GetMapping("/error")
	public String showErrorPage() {
		return "error";
	}
	
	@GetMapping("/")
	public String showIndexPage() {
		return "index";
	}
	
	@GetMapping("/admin")
	public String showAdminPage() {
		return "admin/index";
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
