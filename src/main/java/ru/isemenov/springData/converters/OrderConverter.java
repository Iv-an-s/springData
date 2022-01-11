package ru.isemenov.springData.converters;

import org.springframework.stereotype.Component;
import ru.isemenov.springData.dto.OrderDto;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Order;
import ru.isemenov.springData.entities.Product;

@Component
public class OrderConverter {

    public Order dtoToEntity(OrderDto orderDto){
        return new Order(orderDto.getUserId(), orderDto.getPrice(), orderDto.getAddress(), orderDto.getPhone());
    }
    public OrderDto entityToDto(Order order){
        return new OrderDto(order.getId(), order.getUserId(), order.getPrice(), order.getAddress(), order.getPhone());
    }
}
