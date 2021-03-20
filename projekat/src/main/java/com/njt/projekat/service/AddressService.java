package com.njt.projekat.service;

import com.njt.projekat.entity.Address;
import com.njt.projekat.entity.User;

public interface AddressService {

	Address findByUser(User user);

	Address save(Address address);

}
