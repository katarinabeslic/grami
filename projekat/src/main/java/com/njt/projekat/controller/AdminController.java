package com.njt.projekat.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.njt.projekat.entity.Artist;
import com.njt.projekat.entity.Format;
import com.njt.projekat.entity.RecordLabel;
import com.njt.projekat.entity.Song;
import com.njt.projekat.entity.Vinyl;
import com.njt.projekat.form.VinylFilterForm;
import com.njt.projekat.service.FormatService;
import com.njt.projekat.service.GenreService;
import com.njt.projekat.service.RecordLabelService;
import com.njt.projekat.service.VinylService;
import com.njt.projekat.util.SortFilter;

@Controller
public class AdminController {

	private VinylService vinylService;
	private FormatService formatService;
	private RecordLabelService recordLabelService;
	private GenreService genreService;

	@Autowired
	public AdminController(VinylService vinylService, FormatService formatService,
			RecordLabelService recordLabelService, GenreService genreService) {
		this.vinylService = vinylService;
		this.formatService = formatService;
		this.recordLabelService = recordLabelService;
		this.genreService = genreService;
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
		return "redirect:catalogue";
	}

}
