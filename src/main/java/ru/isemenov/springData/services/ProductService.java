package ru.isemenov.springData.services;

import org.springframework.stereotype.Service;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product is not found: " + id);
        }
    }

    public List<Product> findAllByPriceBetween(Integer min, Integer max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public List<Product> findProductsWithPriceMoreThanMin(Integer min) {
        return productRepository.findProductsWithPriceMoreThanMin(min);
    }

    public List<Product> findProductsWithPriceLessThanMax(Integer max) {
        return productRepository.findProductsWithPriceLessThanMax(max);
    }

    @Transactional
    public void changePrice(Long productID, Integer delta) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Unable to change product's price. Product not found, id: " + productID));
        product.setPrice(product.getPrice() + delta);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
