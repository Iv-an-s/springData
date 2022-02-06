package com.geekbrains.isemenov.spring.web.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.core.entities.Product;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

}
