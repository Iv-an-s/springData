package com.geekbrains.isemenov.spring.web.api.analytics;

public class ProductAnalyticsDto {
    private Long productId;
    private String title;
    private int quantity;

    public ProductAnalyticsDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductAnalyticsDto(Long productId, String title, int quantity) {
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
