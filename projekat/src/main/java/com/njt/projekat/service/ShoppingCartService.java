package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;

public interface ShoppingCartService {

	CartItem save(CartItem cartItem);

	int getNumberOfItems(User user);

	List<CartItem> findAllByUser(User user);

	void saveAll(List<CartItem> shoppingCart);

}
