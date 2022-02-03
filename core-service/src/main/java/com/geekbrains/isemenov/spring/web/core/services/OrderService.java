package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.geekbrains.isemenov.spring.web.core.dto.Cart;
import com.geekbrains.isemenov.spring.web.core.dto.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.core.entities.Order;
import com.geekbrains.isemenov.spring.web.core.entities.OrderItem;
import com.geekbrains.isemenov.spring.web.core.repositories.OrdersRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrdersRepository ordersRepository;
    private final ProductsService productsService;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        String cartKey = cartService.getCartUuidFromSuffix(username);
        Cart currentCart = cartService.getCurrentCart(cartKey);

        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());

        List<OrderItem> items = currentCart.getItems().stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem();
                        item.setOrder(order);
                        item.setQuantity(orderItemDto.getQuantity());
                        item.setPricePerProduct(orderItemDto.getPricePerProduct());
                        item.setPrice(orderItemDto.getPrice());
                        item.setProduct(productsService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartService.clearCart(cartKey);
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }
}
