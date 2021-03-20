package com.njt.projekat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.AddressRepository;
import com.njt.projekat.entity.Address;
import com.njt.projekat.entity.User;
import com.njt.projekat.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address findByUser(User user) {
		return addressRepository.findByUser(user);
	}

	@Override
	public Address save(Address address) {
		return addressRepository.save(address);
	}

}
