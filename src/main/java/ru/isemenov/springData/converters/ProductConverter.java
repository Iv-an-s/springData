package ru.isemenov.springData.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.services.CategoryService;

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
