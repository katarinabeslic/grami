package com.njt.projekat.dao;

import java.util.List;

import com.njt.projekat.entity.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.njt.projekat.entity.CartItem;
import com.njt.projekat.entity.User;

import javax.transaction.Transactional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<CartItem, Integer> {

	int countDistinctByUserAndOrderIsNull(User user);

	CartItem findById(int id);

	List<CartItem> findAllByUser(User user);

    List<CartItem> findAllByUserAndOrderIsNull(User user);

    CartItem findByUserAndVinylAndOrderIsNull(User user, Vinyl vinyl);

    @Transactional
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity=?2 WHERE c.id=?1")
    void updateCartItemQty(int cartItemId, int newQuantity);
}
