package ru.isemenov.springData.converters;

import org.springframework.stereotype.Component;
import ru.isemenov.springData.dto.OrderDto;
import ru.isemenov.springData.dto.OrderItemDto;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Order;
import ru.isemenov.springData.entities.OrderItem;
import ru.isemenov.springData.entities.Product;

@Component
public class OrderItemConverter {

    public OrderItem dtoToEntity(OrderItemDto orderItemDto){
        return new OrderItem(); // todo дописать
    }
    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
