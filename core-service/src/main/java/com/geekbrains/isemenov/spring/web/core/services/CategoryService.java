package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.geekbrains.isemenov.spring.web.core.entities.Category;
import com.geekbrains.isemenov.spring.web.core.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findByName(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException(String.format("Категория %s не найдена", title)));
    }
}
