package com.njt.projekat.service;

import com.njt.projekat.entity.*;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);

    Order save(double cartTotal, User user, Address address, CardInformation cardInformation, List<CartItem> shoppingCart);

    List<Order> findByUser(User user);

    List<Order> findAll();

    void deleteById(int id);

    Order findById(int id);
}
