package com.geekbrains.isemenov.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", required = true)
    private Long id;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
    private String title;

    @Schema(description = "Цена продукта", required = true, example = "120")
    private BigDecimal price;

    public ProductDto(Long id, String title, BigDecimal price) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static ProductDtoBuilder newProductDtoBuilder() {
        return new ProductDto().new ProductDtoBuilder();
    }

    public class ProductDtoBuilder{
        private ProductDtoBuilder(){
        }

        public ProductDtoBuilder setProductDtoId(Long id){
            ProductDto.this.setId(id);
            return this;
        }

        public ProductDtoBuilder setProductDtoTitle(String title){
            ProductDto.this.setTitle(title);
            return this;
        }

        public ProductDtoBuilder setProductDtoPrice(BigDecimal price){
            ProductDto.this.setPrice(price);
            return this;
        }

        public ProductDto build(){
            return ProductDto.this;
        }
    }
}
