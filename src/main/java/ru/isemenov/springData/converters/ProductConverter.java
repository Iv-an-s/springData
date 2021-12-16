package ru.isemenov.springData.converters;

import org.springframework.stereotype.Component;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Product;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto){
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }
    public ProductDto entityToDto(Product product){
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
