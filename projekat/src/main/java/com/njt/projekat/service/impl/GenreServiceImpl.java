package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.GenreRepository;
import com.njt.projekat.entity.Genre;
import com.njt.projekat.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	private GenreRepository genreRepository;
	
	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Genre> findAll() {
		return genreRepository.findAll();
	}

	@Override
	public void save(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public Genre findByName(String name) {
		return genreRepository.findByName(name);
	}

}
