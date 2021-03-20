package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.FormatRepository;
import com.njt.projekat.entity.Format;
import com.njt.projekat.service.FormatService;

@Service
public class FormatServiceImpl implements FormatService {
	
	private FormatRepository formatRepository;
	
	@Autowired
	public FormatServiceImpl(FormatRepository formatRepository) {
		super();
		this.formatRepository = formatRepository;
	}

	@Override
	public List<Format> findAll() {
		return formatRepository.findAll();
	}

}
