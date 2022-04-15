package com.geekbrains.isemenov.spring.web.core.controllers;

import com.geekbrains.isemenov.spring.web.api.core.CategoryDto;
import com.geekbrains.isemenov.spring.web.core.converters.CategoryConverter;
import com.geekbrains.isemenov.spring.web.core.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {
    public final CategoriesService categoriesService;
    public final CategoryConverter categoryConverter;


    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id){
        return categoryConverter.entityToDto(categoriesService.findByIdWithProducts(id).get());
    }
}
