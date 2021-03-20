package com.njt.projekat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;
import com.njt.projekat.entity.Vinyl;
import com.njt.projekat.form.VinylFilterForm;
import com.njt.projekat.service.ShoppingCartService;
import com.njt.projekat.service.FormatService;
import com.njt.projekat.service.GenreService;
import com.njt.projekat.service.UserService;
import com.njt.projekat.service.VinylService;
import com.njt.projekat.util.SortFilter;

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
	
	@GetMapping("/shop")
	public String showUserShop(@ModelAttribute("filters") VinylFilterForm filters, Model model) {
		System.out.println("FILTERS: " + filters);
		if (filters.getFormat() == "") {
			filters.setFormat(null);
		}
		Integer page = filters.getPage();
		int pageNumber = (page == null || page <= 0) ? 0 : page - 1;
		SortFilter sortFilter = new SortFilter(filters.getSort());
		Page<Vinyl> pageResult = vinylService.findVinylsByCriteria(
				PageRequest.of(pageNumber, 9, sortFilter.getSortType()), filters.getFormat(), filters.getGenres(), filters.getSearch()); 

		String classActiveSort = "active" + filters.getSort();
		classActiveSort = classActiveSort.replaceAll("\\s+", "");
		classActiveSort = classActiveSort.replaceAll("&", "");
		model.addAttribute(classActiveSort, true);
		model.addAttribute("vinyls", pageResult.getContent());
		model.addAttribute("formats", formatService.findAll());
		model.addAttribute("genres", genreService.findAll());
		model.addAttribute("totalItems", pageResult.getTotalElements());
		model.addAttribute("itemsPerPage", 9);
		return "shop";
	}

	@GetMapping("/shop/vinyl")
	public String showUserVinyl(@RequestParam("id") int id, Model model) {
		model.addAttribute("vinyl", vinylService.findById(id));
		return "vinyl-detail";
	}
	
	@GetMapping("/shop/add-to-cart")
	public String addItemToCart(@RequestParam("id") Vinyl vinyl, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		User user = userService.findByUsername(principal.getName());
		CartItem cartItem = new CartItem(vinyl, 1, user);
		cartItem = shoppingCartService.save(cartItem);
		return "redirect:/shop";
	}
	
	//??????????????????????????
	@GetMapping("/update-cart")
	public String updateShoppingCart(@ModelAttribute List<CartItem> shoppingCart, Model model, Principal principal) {
		//shoppingCartService.saveAll(shoppingCart);
		System.out.println(shoppingCart);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String showUserCheckout(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<CartItem> shoppingCart = shoppingCartService.findAllByUser(user);
		model.addAttribute("user", user);
		model.addAttribute("shoppingCart", shoppingCart);
		System.out.println(user);
		System.out.println(user.getAddress());
		System.out.println(user.getCardInformation());
		System.out.println(shoppingCart);
		return "checkout";
	}
	
	@PostMapping("/card-payment")
	public String processCardPayment() {
		return "";
	}
}
