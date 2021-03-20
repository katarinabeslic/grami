package com.njt.projekat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<CartItem, Integer> {

	int countDistinctByUser(User user);

	List<CartItem> findAllByUser(User user);

}
