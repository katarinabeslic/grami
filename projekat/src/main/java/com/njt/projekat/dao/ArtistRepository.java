package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

	Artist findByStageName(String stageName);

}
