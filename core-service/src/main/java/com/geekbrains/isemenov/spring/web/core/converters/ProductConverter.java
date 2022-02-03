package com.geekbrains.isemenov.spring.web.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.core.dto.ProductDto;
import com.geekbrains.isemenov.spring.web.core.entities.Product;
import com.geekbrains.isemenov.spring.web.core.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice(), categoryService.findByName(productDto.getCategory()));
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

}
