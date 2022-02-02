package com.geekbrains.isemenov.spring.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.geekbrains.isemenov.spring.web.dto.Cart;
import com.geekbrains.isemenov.spring.web.dto.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.entities.Order;
import com.geekbrains.isemenov.spring.web.entities.OrderItem;
import com.geekbrains.isemenov.spring.web.entities.User;
import com.geekbrains.isemenov.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.repositories.OrdersRepository;

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
    public void createOrder(User user, OrderDetailsDto orderDetailsDto) {
        String cartKey = cartService.getCartUuidFromSuffix(user.getUsername());
        Cart currentCart = cartService.getCurrentCart(cartKey);

        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUser(user);
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
