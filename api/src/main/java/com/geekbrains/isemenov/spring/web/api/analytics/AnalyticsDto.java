package com.geekbrains.isemenov.spring.web.api.analytics;

import com.geekbrains.isemenov.spring.web.api.carts.CartItemDto;

import java.util.List;

public class AnalyticsDto {
    private List<CartItemDto> cartItemDtoList;

    public List<CartItemDto> getCartItemDtoList() {
        return cartItemDtoList;
    }

    public void setCartItemDtoList(List<CartItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }

    public AnalyticsDto() {
    }

    public AnalyticsDto(List<CartItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }
}
