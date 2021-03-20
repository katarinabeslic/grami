package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.RecordLabel;

@Repository
public interface RecordLabelRepository extends JpaRepository<RecordLabel, Integer> {

	@Query("SELECT r FROM RecordLabel r WHERE r.name=?1 AND r.year=?2")
	RecordLabel findOneByNameAndYear(String name, String year);

}
