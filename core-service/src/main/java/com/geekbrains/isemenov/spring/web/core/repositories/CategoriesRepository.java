package com.geekbrains.isemenov.spring.web.core.repositories;

import com.geekbrains.isemenov.spring.web.core.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}