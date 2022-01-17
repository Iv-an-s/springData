package ru.isemenov.springData.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.dto.Cart;
import ru.isemenov.springData.dto.OrderDetailsDto;
import ru.isemenov.springData.entities.Order;
import ru.isemenov.springData.entities.OrderItem;
import ru.isemenov.springData.entities.User;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.OrdersRepository;

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
