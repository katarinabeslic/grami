package com.njt.projekat.service.impl;

import java.util.List;

import com.njt.projekat.entity.Vinyl;
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
	public int countDistinctByUserAndOrderIsNull(User user) {
		return shoppingCartRepository.countDistinctByUserAndOrderIsNull(user);
	}

	@Override
	public List<CartItem> findAllByUser(User user) {
		return shoppingCartRepository.findAllByUser(user);
	}

	@Override
	public void saveAll(List<CartItem> shoppingCart) {
		shoppingCartRepository.saveAll(shoppingCart);		
	}

	@Override
	public List<CartItem> findAllByUserAndOrderIsNull(User user) {
		return shoppingCartRepository.findAllByUserAndOrderIsNull(user);
	}

	@Override
	public void removeCartItemById(int id) {
		shoppingCartRepository.deleteById(id);
	}

	@Override
	public CartItem findByUserAndVinylAndOrderIsNull(User user, Vinyl vinyl) {
		return shoppingCartRepository.findByUserAndVinylAndOrderIsNull(user, vinyl);
	}

}
