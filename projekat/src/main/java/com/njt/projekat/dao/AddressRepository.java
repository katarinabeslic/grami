package com.njt.projekat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.njt.projekat.entity.Address;
import com.njt.projekat.entity.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	Address findByUser(User user);

}
