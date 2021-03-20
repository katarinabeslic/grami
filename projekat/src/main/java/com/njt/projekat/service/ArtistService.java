package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.Artist;

public interface ArtistService {

	List<Artist> findAll();

	Artist save(Artist artist);

	Artist findByStageName(String stageName);

}
