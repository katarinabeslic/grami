package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

}
