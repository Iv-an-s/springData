package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.core.entities.Order;

import java.util.Optional;

public interface OrderServisable {
    public Optional<Order> findById(Long orderId);
}
