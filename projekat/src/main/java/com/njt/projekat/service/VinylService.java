package com.njt.projekat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.njt.projekat.entity.Vinyl;

public interface VinylService {

	public Vinyl save(Vinyl vinyl);

	public List<Vinyl> findAll();

	public Vinyl findById(int id);

	public void deleteById(int id);

	public List<Vinyl> findAllOrderByPriceDesc();

	public List<Vinyl> findByFormatId(int id);

	public Page<Vinyl> findVinylsByCriteria(Pageable pageable, String format, List<String> genres, String search);

}
