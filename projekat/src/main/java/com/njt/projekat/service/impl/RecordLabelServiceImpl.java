package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.RecordLabelRepository;
import com.njt.projekat.entity.RecordLabel;
import com.njt.projekat.service.RecordLabelService;

@Service
public class RecordLabelServiceImpl implements RecordLabelService {

	private RecordLabelRepository recordLabelRepository;
	
	@Autowired
	public RecordLabelServiceImpl(RecordLabelRepository recordLabelRepository) {
		super();
		this.recordLabelRepository = recordLabelRepository;
	}

	@Override
	public List<RecordLabel> findAll() {
		return recordLabelRepository.findAll();
	}

	@Override
	public void save(RecordLabel recordLabel) {
		recordLabelRepository.save(recordLabel);		
	}

	@Override
	public RecordLabel findOneByNameAndYear(String name, String year) {
		return recordLabelRepository.findOneByNameAndYear(name, year);
	}

}
