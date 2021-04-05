package com.njt.projekat.service;

import java.util.List;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;
import com.njt.projekat.entity.Vinyl;

public interface ShoppingCartService {

	CartItem save(CartItem cartItem);

	int countDistinctByUserAndOrderIsNull(User user);

	List<CartItem> findAllByUser(User user);

	void saveAll(List<CartItem> shoppingCart);

	List<CartItem> findAllByUserAndOrderIsNull(User user);

    void removeCartItemById(int id);

	CartItem findByUserAndVinylAndOrderIsNull(User user, Vinyl vinyl);
}
