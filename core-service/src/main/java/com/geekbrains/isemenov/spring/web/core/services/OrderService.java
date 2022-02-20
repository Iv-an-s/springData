package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartItemDto;
import com.geekbrains.isemenov.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.api.core.OrderItemDto;
import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.core.converters.OrderItemConverter;
import com.geekbrains.isemenov.spring.web.core.entities.Order;
import com.geekbrains.isemenov.spring.web.core.entities.OrderItem;
import com.geekbrains.isemenov.spring.web.core.integrations.CartServiceIntegration;
import com.geekbrains.isemenov.spring.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartServiceIntegration cartServiceIntegration;
    private final OrdersRepository ordersRepository;
    private final ProductsService productsService;
    private final OrderItemConverter orderItemConverter;

    private List<OrderItemDto> productMonthCounter = new ArrayList<>();

    public AnalyticsDto getMonthProductsAnalytics(){
        List<CartItemDto> productCounterCopy = new ArrayList<>();
        for (int i = 0; i < productMonthCounter.size(); i++) {
            OrderItemDto tempItem = productMonthCounter.get(i);
            productCounterCopy.add(new CartItemDto(tempItem.getProductId(), tempItem.getProductTitle(), tempItem.getQuantity(), tempItem.getPricePerProduct(), tempItem.getPrice()));
        }
        productMonthCounter.clear();
        return new AnalyticsDto(productCounterCopy);
    }

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
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
        cartServiceIntegration.clearUserCart(username);
        for (int i = 0; i < items.size(); i++) {
            productMonthCounter.add(orderItemConverter.entityToDto(items.get(i)));
        }
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }
}
