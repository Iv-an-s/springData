package ru.isemenov.springData.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.dto.OrderItemDto;
import ru.isemenov.springData.entities.OrderItem;
import ru.isemenov.springData.repositories.OrderItemRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void saveOrderItemsFromOrder(List<OrderItemDto> itemDtoList, Long orderId) {
        for (OrderItemDto o : itemDtoList) {
            OrderItem orderItem = new OrderItem(o.getProductId(), orderId, o.getQuantity(), o.getPricePerProduct(), o.getPrice());
            orderItemRepository.save(orderItem);
        }
    }
}
