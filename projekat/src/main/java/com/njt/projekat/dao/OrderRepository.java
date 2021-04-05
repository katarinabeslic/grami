package com.njt.projekat.dao;

import com.njt.projekat.entity.Order;
import com.njt.projekat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUser(User user);

    Order findById(int id);
}
