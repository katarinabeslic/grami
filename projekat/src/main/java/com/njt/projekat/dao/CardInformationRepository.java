package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.CardInformation;
import com.njt.projekat.entity.User;

@Repository
public interface CardInformationRepository extends JpaRepository<CardInformation, Integer> {

	CardInformation findByUser(User user);

}
