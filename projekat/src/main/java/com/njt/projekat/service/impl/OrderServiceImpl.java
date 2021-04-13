package com.njt.projekat.service.impl;

import com.njt.projekat.dao.OrderRepository;
import com.njt.projekat.entity.*;
import com.njt.projekat.service.OrderService;
import com.njt.projekat.service.ShoppingCartService;
import com.njt.projekat.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final VinylService vinylService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, VinylService vinylService, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.vinylService = vinylService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order save(double cartTotal, User user, Address address, CardInformation cardInformation, List<CartItem> shoppingCart) {
        Order order = new Order();
        order.setCurrency("RSD");
        order.setDateAndTime(Timestamp.valueOf(LocalDateTime.now()));
        order.setOrderStatus("Received");
        order.setPaymentMethod("card");
        order.setTotalPrice(cartTotal);
        order.setUser(user);
        order.setCartItems(shoppingCart);
        order.setCardInformation(cardInformation);
        order.setAddress(address);
        order = orderRepository.save(order);
        for (CartItem cartItem: shoppingCart) {
            Vinyl vinyl = cartItem.getVinyl();
            vinyl.decreaseStock(cartItem.getQuantity());
            vinylService.save(vinyl);
            cartItem.setOrder(order);
            shoppingCartService.save(cartItem);
        }
        return order;
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        Order order = orderRepository.findById(id);
        System.out.println(order);
        List<CartItem> boughtItems = order.getCartItems();
        for (CartItem item : boughtItems) {
            if (!order.getOrderStatus().equals("Cancelled")) {
                int quantity = item.getQuantity();
                item.getVinyl().increaseStock(quantity);
                vinylService.save(item.getVinyl());
            }
            shoppingCartService.removeCartItemById(item.getId());
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id);
    }
}
