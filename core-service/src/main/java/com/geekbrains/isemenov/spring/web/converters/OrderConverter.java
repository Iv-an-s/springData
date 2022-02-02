package com.geekbrains.isemenov.spring.web.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.dto.OrderDto;
import com.geekbrains.isemenov.spring.web.dto.OrderItemDto;
import com.geekbrains.isemenov.spring.web.entities.Order;
import com.geekbrains.isemenov.spring.web.entities.OrderItem;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDto orderDto){
        return new Order(); // todo дописать
    }

    public OrderDto entityToDto(Order order){
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setAddress(order.getAddress());
        out.setPhone(order.getPhone());
        out.setTotalPrice(order.getTotalPrice());
        out.setUsername(order.getUser().getUsername());
        out.setItems(order.getItems().stream().map(orderItemConverter :: entityToDto).collect(Collectors.toList()));
        return out;
    }
}
