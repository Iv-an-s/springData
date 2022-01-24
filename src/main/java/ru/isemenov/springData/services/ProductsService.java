package ru.isemenov.springData.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.dto.CategoryDto;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.repositories.ProductsRepository;
import ru.isemenov.springData.repositories.specifications.ProductsSpecifications;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String titlePart, CategoryDto category, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ProductsSpecifications.titleLike(titlePart));
        }
        if (category != null) {
            spec = spec.and(ProductsSpecifications.categoryTitleIs(category.getTitle()));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public List<Product> findAllBy() {
        return productsRepository.findAllBy();
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product is not found: " + id);
        }
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public void changePrice(Long productID, Integer delta) {
        Product product = productsRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Unable to change product's price. Product not found, id: " + productID));
        product.setPrice(product.getPrice() + delta);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить  продукт, не найден в базе, id: " + productDto.getId()));
        if (productDto.getTitle() != null) {
            product.setTitle(productDto.getTitle());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        return product;
    }
}
