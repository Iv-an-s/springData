package com.geekbrains.isemenov.spring.web.core.converters;

import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.core.entities.Category;
import com.geekbrains.isemenov.spring.web.core.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.core.entities.Product;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoriesService categoriesService;

    public Product dtoToEntity(ProductDto productDto) {
        Category category = categoriesService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()-> new ResourceNotFoundException("Wrong category title: " + productDto.getCategoryTitle()));
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        return product;
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

}
