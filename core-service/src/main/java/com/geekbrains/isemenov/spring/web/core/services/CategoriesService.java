package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.core.entities.Category;
import com.geekbrains.isemenov.spring.web.core.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public Optional<Category> findByIdWithProducts(Long id) {
        return categoriesRepository.findById(id);
    }
    public Optional<Category> findByTitle(String title) {
        return categoriesRepository.findByTitle(title);
    }
}
