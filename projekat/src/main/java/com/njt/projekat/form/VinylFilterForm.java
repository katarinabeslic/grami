package com.njt.projekat.form;

import java.util.List;

public class VinylFilterForm {
	
	private String format;
	private List<String> genres;
	private String sort;
	private Integer page;
	private String search;
	
	public VinylFilterForm() {
	
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "VinylFilterForm [format=" + format + ", genres=" + genres + ", sort=" + sort + ", page=" + page
				+ ", search=" + search + "]";
	}

}
