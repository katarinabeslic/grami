package com.njt.projekat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.njt.projekat.entity.*;
import com.njt.projekat.service.*;
import com.njt.projekat.service.impl.UserSecurityService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njt.projekat.form.VinylFilterForm;
import com.njt.projekat.util.SortFilter;

@Controller
public class AdminController {

	private VinylService vinylService;
	private FormatService formatService;
	private RecordLabelService recordLabelService;
	private GenreService genreService;
	private OrderService orderService;
	private ArtistService artistService;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Autowired
	public AdminController(VinylService vinylService, FormatService formatService, RecordLabelService recordLabelService,
						   GenreService genreService, OrderService orderService, ArtistService artistService,
						   UserService userService, UserSecurityService userSecurityService) {
		this.vinylService = vinylService;
		this.formatService = formatService;
		this.recordLabelService = recordLabelService;
		this.genreService = genreService;
		this.orderService = orderService;
		this.artistService = artistService;
		this.userService = userService;
	}

	@GetMapping("/admin/catalogue")
	public String showAdminCatalogue(@ModelAttribute("filters") VinylFilterForm filters, Model model) {
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
		return "admin/catalogue";
	}

	@GetMapping("/admin/add")
	public String showAdminAddNew(Model model) {
		model.addAttribute("vinyl", new Vinyl());
		model.addAttribute("artist", new Artist());
		model.addAttribute("format", new Format());
		model.addAttribute("recordLabel", new RecordLabel());
		model.addAttribute("tracklist", new ArrayList<Song>());
		model.addAttribute("formats", formatService.findAll());
		model.addAttribute("recordLabels", recordLabelService.findAll());
		model.addAttribute("genres", genreService.findAll());
		model.addAttribute("artists", artistService.findAll());
		return "admin/add_new";
	}

	@GetMapping("/admin/edit")
	public String showAdminVinylToEdit(@RequestParam("id") int id, Model model) {
		model.addAttribute(vinylService.findById(id));
		model.addAttribute("recordLabels", recordLabelService.findAll());
		model.addAttribute("genres", genreService.findAll());
		model.addAttribute("formats", formatService.findAll());
		return "admin/edit_vinyl";
	}

	@GetMapping("/admin/remove")
	public String redirectAdminToCatalogue(@RequestParam("id") int id, Model model) {
		vinylService.deleteById(id);
		model.addAttribute("vinyls", vinylService.findAll());
		model.addAttribute("formats", formatService.findAll());
		model.addAttribute("genres", genreService.findAll());
		return "redirect:/admin/catalogue";
	}

	@GetMapping("/admin/orders")
	public String showAdminOrders(Model model) {
		model.addAttribute("orders", orderService.findAll());
		return "admin/orders";
	}

	@GetMapping("/admin/update-order")
	public String showEditOrder(@RequestParam("id") int id, Model model) {
		model.addAttribute("order", orderService.findById(id));
		return "admin/update-order";
	}

	@PostMapping("/admin/update-order")
	public String changeOrderStatus(@ModelAttribute("order") Order order, Model model) {
		Order existingOrder = orderService.findById(order.getId());
		String status = order.getOrderStatus();
		if (status.equals("Cancelled")) {
			for (CartItem item: existingOrder.getCartItems()) {
				int quantity = item.getQuantity();
				item.getVinyl().increaseQuantity(quantity);
				vinylService.save(item.getVinyl());
			}
		}
		existingOrder.setOrderStatus(order.getOrderStatus());
		orderService.save(existingOrder);
		return "redirect:/admin/orders";
	}

	@GetMapping("/admin/users")
	public String showAdminUsers(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@GetMapping("/admin/remove-user")
	public String removeUser(@RequestParam("id") int id) {
		userService.deleteById(id);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/add-user")
	public String showAdminAddUser(Model model) {
		model.addAttribute("user", new User());
		return "admin/add-user";
	}

	@PostMapping("/create-user")
	public String createNewUser(@ModelAttribute("user") User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.createUser(user, Arrays.asList("ROLE_CUSTOMER"));
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/edit-user")
	public String showAdminEditUser(@RequestParam("id") int id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "admin/edit-user";
	}

	@PostMapping("/admin/edit-user")
	public String editUser(@ModelAttribute("user") User user) {
		System.out.println(user.getId());
		System.out.println(user);
		return "redirect:/admin/users";
	}
}
