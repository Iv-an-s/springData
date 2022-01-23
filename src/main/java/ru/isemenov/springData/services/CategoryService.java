package ru.isemenov.springData.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.entities.Category;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findByName(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException(String.format("Категория %s не найдена", title)));
    }
}
