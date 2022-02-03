package com.geekbrains.isemenov.spring.web.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.geekbrains.isemenov.spring.web.core.entities.Product;

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

    public static Specification<Product> categoryTitleIs(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("title"), title);
    }

}
