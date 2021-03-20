package com.njt.projekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.ShoppingCartRepository;
import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;
import com.njt.projekat.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Override
	public CartItem save(CartItem cartItem) {
		return shoppingCartRepository.save(cartItem);
	}

	@Override
	public int getNumberOfItems(User user) {
		return shoppingCartRepository.countDistinctByUser(user);
	}

	@Override
	public List<CartItem> findAllByUser(User user) {
		return shoppingCartRepository.findAllByUser(user);
	}

	@Override
	public void saveAll(List<CartItem> shoppingCart) {
		shoppingCartRepository.saveAll(shoppingCart);		
	}

}
