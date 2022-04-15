package com.geekbrains.isemenov.spring.web.core.repositories.specifications;

import com.geekbrains.isemenov.spring.web.core.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessOrEqualsThan(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Product> categoryLike(String categoryTitlePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category"), String.format("%%%s%%", categoryTitlePart));
    }
}
