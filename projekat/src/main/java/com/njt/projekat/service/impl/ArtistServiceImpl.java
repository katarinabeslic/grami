package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.ArtistRepository;
import com.njt.projekat.entity.Artist;
import com.njt.projekat.service.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService {

	private ArtistRepository artistRepository;

	@Autowired
	public ArtistServiceImpl(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	@Override
	public List<Artist> findAll() {
		return artistRepository.findAll();
	}

	@Override
	public Artist save(Artist artist) {
		return artistRepository.save(artist);
	}

	@Override
	public Artist findByStageName(String stageName) {
		return artistRepository.findByStageName(stageName);
	}	
	
	
}
