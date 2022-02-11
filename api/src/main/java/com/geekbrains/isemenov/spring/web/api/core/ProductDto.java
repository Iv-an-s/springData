package com.geekbrains.isemenov.spring.web.api.core;

public class ProductDto {
    private Long id;
    private String title;
    private Integer price;

    public ProductDto(Long id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}