package com.geekbrains.isemenov.spring.web.api.core;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String city;
    private String address;
    private String status;
    private String phone;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, List<OrderItemDto> items, BigDecimal totalPrice, String city, String address, String status, String phone) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.totalPrice = totalPrice;
        this.city = city;
        this.address = address;
        this.status = status;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
