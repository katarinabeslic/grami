package com.njt.projekat.dao;

import java.util.List;

import com.njt.projekat.entity.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<CartItem, Integer> {

	int countDistinctByUserAndOrderIsNull(User user);

	List<CartItem> findAllByUser(User user);

    List<CartItem> findAllByUserAndOrderIsNull(User user);

    CartItem findByUserAndVinylAndOrderIsNull(User user, Vinyl vinyl);
}
