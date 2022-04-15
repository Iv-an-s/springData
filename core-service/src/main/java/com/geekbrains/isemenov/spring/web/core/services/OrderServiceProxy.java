package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.core.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderServiceProxy implements OrderServisable{
    @Autowired
    private OrderService orderService;
    private Map<Long, Optional<Order>> cache;

    public OrderServiceProxy() {
        cache = new HashMap<>();
    }

    @Override
    public Optional<Order>findById(Long orderId){
        if(!cache.containsKey(orderId)){
            Optional<Order> order = orderService.findById(orderId);
            cache.put(orderId, order);
            return order;
        }
        return cache.get(orderId);
    }
}
