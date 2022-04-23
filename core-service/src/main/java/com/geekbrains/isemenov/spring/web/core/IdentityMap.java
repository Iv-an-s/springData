package com.geekbrains.isemenov.spring.web.core;

import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.core.entities.Product;
import com.geekbrains.isemenov.spring.web.core.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IdentityMap {
    @Autowired
    private ProductsRepository productsRepository;
    private final Map<Long, Product> identityMap;

    public IdentityMap() {
        identityMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        identityMap.put(product.getId(), product);
    }

    public Product getProduct(Long key) {
        if (identityMap.containsKey(key)) {
            return identityMap.get(key);
        }
        Product product = productsRepository.findById(key).orElseThrow(() -> new ResourceNotFoundException("Product with id: " + key + " not found"));
        addProduct(product);
        return product;
    }
}
