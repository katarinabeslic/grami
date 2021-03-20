package com.njt.projekat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.Vinyl;

@Repository
public interface VinylRepository extends JpaRepository<Vinyl, Integer>, JpaSpecificationExecutor<Vinyl>, PagingAndSortingRepository<Vinyl, Integer> {

	Vinyl findById(int id);

	@Query("SELECT v FROM Vinyl v ORDER BY v.price DESC")
	List<Vinyl> findAllOrderByPriceDesc();

	@Query("SELECT v From Vinyl v WHERE v.format.id=?1")
	List<Vinyl> findByFormatId(int id);

}
