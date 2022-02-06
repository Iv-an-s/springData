package com.geekbrains.isemenov.spring.web.cart.converters;

import com.geekbrains.isemenov.spring.web.api.carts.CartDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartItemDto;
import com.geekbrains.isemenov.spring.web.cart.models.Cart;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {

    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getItems().stream().map(it ->
                new CartItemDto(it.getProductId(), it.getProductTitle(), it.getQuantity(), it.getPricePerProduct(), it.getPrice())
        ).collect(Collectors.toList());
        return new CartDto(cartItemDtos, cart.getTotalPrice());
    }

}
