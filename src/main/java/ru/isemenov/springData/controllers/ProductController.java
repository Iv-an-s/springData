package ru.isemenov.springData.controllers;

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

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

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

    @GetMapping("/products/price_more/{min_price}")
    public List<Product> findProductsWithPriceMoreThanMin(@PathVariable(name = "min_price") Integer min) {
        return productService.findProductsWithPriceMoreThanMin(min);
    }

    @GetMapping("/products/price_less/{max_price}")
    public List<Product> findProductsWithPriceLessThanMax(@PathVariable(name = "max_price") Integer max) {
        return productService.findProductsWithPriceLessThanMax(max);
    }
}
