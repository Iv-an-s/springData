package ru.isemenov.springData.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.ProductRepository;
import ru.isemenov.springData.repositories.specifications.ProductSpecifications;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String namePart, Integer page){
        Specification <Product> spec = Specification.where(null);
        if(minPrice != null){
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null){
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThan(maxPrice));
        }
        if (namePart != null){
            spec = spec.and(ProductSpecifications.nameLike(namePart));
        }

        return productRepository.findAll(spec, PageRequest.of(page -1, 5));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) { //todo исправить
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product is not found: " + id);
        }
    }

    @Transactional
    public void changePrice(Long productID, Integer delta) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Unable to change product's price. Product not found, id: " + productID));
        product.setPrice(product.getPrice() + delta);
    }

    public List<Product> findAllByPriceBetween(Integer min, Integer max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
