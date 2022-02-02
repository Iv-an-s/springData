package com.geekbrains.isemenov.spring.web.validators;

import org.springframework.stereotype.Component;
import com.geekbrains.isemenov.spring.web.dto.ProductDto;
import com.geekbrains.isemenov.spring.web.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProductValidator {
    public void validate(ProductDto productDto){
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice() < 1){
            errors.add("Цена продукта не может быть меньше 1");
        }
        if (productDto.getTitle().isBlank()){
            errors.add("Название продукта не может быть пустым");
        }
        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
