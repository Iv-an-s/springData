package ru.isemenov.springData.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.converters.OrderConverter;
import ru.isemenov.springData.dto.Cart;
import ru.isemenov.springData.dto.OrderDto;
import ru.isemenov.springData.dto.OrderInfoDto;
import ru.isemenov.springData.entities.Order;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.OrderRepository;
import ru.isemenov.springData.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final OrderConverter orderConverter;

    public OrderDto createOrder(OrderInfoDto orderDto, String username) {
        Cart cart = cartService.getCurrentCart();
        Long userId = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("there is no user in database with username: " + username)).getId();
        int totalPrice = cart.getTotalPrice();
        String address = orderDto.getAddress();
        String phone = orderDto.getPhone();
        Order order = orderRepository.save(new Order(userId, totalPrice, address, phone));
        orderItemService.saveOrderItemsFromOrder(cart.getItems(), order.getId());
        cartService.clear();
        return orderConverter.entityToDto(order);
    }
}
