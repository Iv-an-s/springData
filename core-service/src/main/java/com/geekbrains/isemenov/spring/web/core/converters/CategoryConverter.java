package com.geekbrains.isemenov.spring.web.core.converters;

import com.geekbrains.isemenov.spring.web.api.core.CategoryDto;
import com.geekbrains.isemenov.spring.web.core.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getTitle());
    }
}
