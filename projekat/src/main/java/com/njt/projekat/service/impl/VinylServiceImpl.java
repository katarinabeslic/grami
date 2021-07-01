package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.VinylRepository;
import com.njt.projekat.entity.Vinyl;
import com.njt.projekat.service.VinylService;
import com.njt.projekat.util.VinylSpecification;

@Service
public class VinylServiceImpl implements VinylService {
	
	private VinylRepository vinylRepository;

	@Autowired
	public VinylServiceImpl(VinylRepository vinylRepository) {
		this.vinylRepository = vinylRepository;
	}

	@Override
	public Vinyl save(Vinyl vinyl) {
		return vinylRepository.save(vinyl);
	}

	@Override
	public List<Vinyl> findAll() {
		return vinylRepository.findAll();
	}

	@Override
	public Vinyl findById(int id) {
		return vinylRepository.findById(id);
	}

	@Override
	public boolean deleteById(int id) {
		vinylRepository.deleteById(id);
        return false;
    }

	@Override
	public List<Vinyl> findAllOrderByPriceDesc() {
		return vinylRepository.findAllOrderByPriceDesc();
	}

	@Override
	public List<Vinyl> findByFormatId(int id) {
		return vinylRepository.findByFormatId(id);
	}

	@Override
	public Page<Vinyl> findVinylsByCriteria(Pageable pageable, String format, List<String> genres, String search) {
		return vinylRepository.findAll(VinylSpecification.filterBy(format, genres, search), pageable);
	}

	@Override
	public Vinyl checkIfExists(String vinylName, String stageName) {
		return vinylRepository.findVinylWhereVinylNameEquals(vinylName, stageName);
	}

}
