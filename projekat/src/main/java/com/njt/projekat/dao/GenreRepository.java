package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

	Genre findByName(String name);

    List<Genre> findAllByOrderByName();
}
