package com.geekbrains.isemenov.spring.web.core.converters;

import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.core.dto.OrderDto;
import com.geekbrains.isemenov.spring.web.core.dto.OrderItemDto;
import com.geekbrains.isemenov.spring.web.core.dto.ProductDto;
import com.geekbrains.isemenov.spring.web.core.entities.Order;
import com.geekbrains.isemenov.spring.web.core.entities.OrderItem;
import com.geekbrains.isemenov.spring.web.core.entities.Product;

@Component
public class OrderItemConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto){
        return new OrderItem(); // todo дописать
    }
    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
