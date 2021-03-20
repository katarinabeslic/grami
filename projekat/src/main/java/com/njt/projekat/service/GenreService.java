package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.Genre;

public interface GenreService {
	
	public List<Genre> findAll();

	public void save(Genre genre);

	public Genre findByName(String name);
	
}
