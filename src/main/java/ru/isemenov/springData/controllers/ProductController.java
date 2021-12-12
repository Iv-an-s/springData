package ru.isemenov.springData.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDto> getAllProducts(
        @RequestParam(name = "p", defaultValue = "1") Integer page,
        @RequestParam(name = "min_price", required = false) Integer minPrice,
        @RequestParam(name = "max_price", required = false) Integer maxPrice,
        @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1){
            page = 1;
        }
        return productService.find(minPrice, maxPrice, namePart, page).map(p -> new ProductDto(p));
    }


    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.findById(id).map(p -> new ProductDto(p)).orElseThrow(() -> new ResourceNotFoundException("Product is not found: " + id));
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
        Product product = new Product(productDto);
        product.setId(null);
        return new ProductDto(productService.save(product));
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto){
        Product product = new Product(productDto);
        return new ProductDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changePrice(productId, delta);
    }
}
