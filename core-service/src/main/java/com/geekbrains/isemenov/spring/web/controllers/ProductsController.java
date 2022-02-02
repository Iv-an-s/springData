package com.geekbrains.isemenov.spring.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.isemenov.spring.web.converters.ProductConverter;
import com.geekbrains.isemenov.spring.web.dto.CategoryDto;
import com.geekbrains.isemenov.spring.web.dto.ProductDto;
import com.geekbrains.isemenov.spring.web.entities.Product;
import com.geekbrains.isemenov.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.services.ProductsService;
import com.geekbrains.isemenov.spring.web.validators.ProductValidator;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "category", required = false) CategoryDto category
    ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minPrice, maxPrice, titlePart, category, page).map(
                p -> productConverter.entityToDto(p));
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product is not found: " + id));
        return productConverter.entityToDto(product);
    }

//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Long id){
//        Optional<Product> product = productService.findById(id);
//        if (product.isPresent()){
//            return new ResponseEntity<>(product.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Product not found, id: " + id), HttpStatus.NOT_FOUND);
//    }

    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product.setId(null); //todo нужно ли обнулять?
        productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productsService.deleteById(id);
    }

    @GetMapping("/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productsService.changePrice(productId, delta);
    }
}
