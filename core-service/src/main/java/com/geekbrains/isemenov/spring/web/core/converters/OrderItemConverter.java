package com.geekbrains.isemenov.spring.web.core.converters;

import com.geekbrains.isemenov.spring.web.api.core.OrderItemDto;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.core.entities.OrderItem;

@Component
public class OrderItemConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto){
        return new OrderItem(); // todo дописать
    }
    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
