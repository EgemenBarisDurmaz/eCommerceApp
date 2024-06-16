package com.example.ecommerceportal.service;

import com.example.ecommerceportal.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(Order order);
    void deleteById(Long id);
}
