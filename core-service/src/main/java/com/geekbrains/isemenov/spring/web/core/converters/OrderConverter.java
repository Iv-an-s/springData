package com.geekbrains.isemenov.spring.web.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.api.core.OrderDto;
import com.geekbrains.isemenov.spring.web.core.entities.Order;

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
        out.setUsername(order.getUsername());
        out.setItems(order.getItems().stream().map(orderItemConverter :: entityToDto).collect(Collectors.toList()));
        return out;
    }
}
