package com.njt.projekat.util;

import org.springframework.data.domain.Sort;

public class SortFilter {

	private String sortType;

	public SortFilter(String sortType) {
		this.sortType = sortType;
	}

	public Sort getSortType() {
		if (this.sortType == null) {
			return Sort.by("id").ascending();
		}
		switch (this.sortType) {
		case "price-asc":
			return Sort.by("price").ascending();
		case "price-desc":
			return Sort.by("price").descending();
		case "alph-asc":
			return Sort.by("vinylName").ascending();
		case "alph-desc":
			return Sort.by("vinylName").descending();
		default:
			return Sort.by("id").descending();
		}
	}

}
