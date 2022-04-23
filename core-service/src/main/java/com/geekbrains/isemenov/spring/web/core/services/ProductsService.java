package com.geekbrains.isemenov.spring.web.core.services;

import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.core.IdentityMap;
import com.geekbrains.isemenov.spring.web.core.converters.ProductConverter;
import com.geekbrains.isemenov.spring.web.core.entities.Category;
import com.geekbrains.isemenov.spring.web.core.entities.Product;
import com.geekbrains.isemenov.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.isemenov.spring.web.core.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductConverter productConverter;
    private final CategoriesService categoriesService;
    private final IdentityMap identityMap;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String titlePart, String categoryTitlePart, Integer page) {
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
        if (categoryTitlePart != null) {
            spec = spec.and(ProductsSpecifications.categoryLike(categoryTitlePart));
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(identityMap.getProduct(id));
        //return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product is not found: " + id);
        }
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productsRepository.save(product);
        return productConverter.entityToDto(persistProduct);
    }

    @Transactional
    public void changePrice(Long productID, Integer delta) {
        Product product = productsRepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Unable to change product's price. Product not found, id: " + productID));
        product.setPrice(product.getPrice().add(BigDecimal.valueOf(delta)));
    }

    @Transactional
    public void updateProductFromDto(ProductDto productDto) {
        Product product = findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить  продукт, не найден в базе, id: " + productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());

        if(!product.getCategory().getTitle().equals(productDto.getCategoryTitle())){
            Category category = categoriesService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()-> new ResourceNotFoundException("wrong category: " + productDto.getCategoryTitle()));
            product.setCategory(category);
        }
        //return productsRepository.save(product);
    }
    // т.к. мы не хотим ничего возвращать, то save() не нужен. Нibernate при коммите транзакции автоматически
    // бы заапдейтит объект.
 }
