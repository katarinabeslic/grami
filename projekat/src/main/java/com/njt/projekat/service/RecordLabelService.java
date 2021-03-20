package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.RecordLabel;

public interface RecordLabelService {

	public List<RecordLabel> findAll();

	public void save(RecordLabel recordLabel);

	public RecordLabel findOneByNameAndYear(String name, String year);
	
}
