package com.geekbrains.isemenov.spring.web.api.core;

public class OrderDetailsDto {
    private String city;
    private String address;
    private String phone;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String city, String address, String phone) {
        this.city = city;
        this.address = address;
        this.phone = phone;
    }
}
