package ru.isemenov.springData.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(
        @RequestParam(name = "p", defaultValue = "1") Integer page,
        @RequestParam(name = "min_price", required = false) Integer minPrice,
        @RequestParam(name = "max_price", required = false) Integer maxPrice,
        @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1){
            page = 1;
        }
        return productService.find(minPrice, maxPrice, namePart, page);
    }


    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product is not found: " + id));
    }

//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Long id){
//        Optional<Product> product = productService.findById(id);
//        if (product.isPresent()){
//            return new ResponseEntity<>(product.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Product not found, id: " + id), HttpStatus.NOT_FOUND);
//    }


    @PostMapping("/products")
    public Product saveNewProduct(@RequestBody Product product) {
        //не имеем права в теле запроса передавать сущности. В этом примере - для упрощения
        return productService.save(product);
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/products/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changePrice(productId, delta);
    }

    @GetMapping("/products/price_between")
    public List<Product> findProductsByPriceBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "0") Integer max) {
        return productService.findAllByPriceBetween(min, max);
    }
}
