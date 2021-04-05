package com.njt.projekat.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;
import com.njt.projekat.service.ShoppingCartService;

@ControllerAdvice
public class MyControllerAdvice {
	
	public static final String DEFAULT_ERROR_VIEW = "error";
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@ModelAttribute
	public void populateModel(Model model) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {				
			User user =  (User) auth.getPrincipal(); 
			if (user != null) {
				double cartTotal = 0;
				List<CartItem> cartItems = shoppingCartService.findAllByUserAndOrderIsNull(user);
				for (CartItem cartItem : cartItems) {
					cartTotal += cartItem.getVinyl().getPrice() * cartItem.getQuantity();
				}
				
				model.addAttribute("shoppingCartItemNumber", shoppingCartService.countDistinctByUserAndOrderIsNull(user));
				model.addAttribute("shoppingCartTotal", cartTotal);
				model.addAttribute("shoppingCart", cartItems);
			}
		} else { 
			model.addAttribute("shoppingCartItemNumber", 0);
			model.addAttribute("shoppingCartTotal", 0);
		} 
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;		
		ModelAndView mav = new ModelAndView();
		mav.addObject("timestamp", new Date(System.currentTimeMillis()));
		mav.addObject("path", req.getRequestURL());
		mav.addObject("message", e.getMessage());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
}
