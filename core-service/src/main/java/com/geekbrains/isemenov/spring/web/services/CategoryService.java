package com.geekbrains.isemenov.spring.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.geekbrains.isemenov.spring.web.entities.Category;
import com.geekbrains.isemenov.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findByName(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException(String.format("Категория %s не найдена", title)));
    }
}
