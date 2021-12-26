package ru.isemenov.springData.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<ProductDto> showCart() {
        return cartService.showCart();
    }

    @GetMapping("/add/{productId}")
    public void addProduct(@PathVariable Long productId) {
        cartService.addProduct(productId);
    }

    @GetMapping("/remove_product/{productId}")
    public void removeProduct(@PathVariable Long productId) {
        cartService.removeProduct(productId);
    }

    @GetMapping("/clear")
    public void clear() {
        cartService.clear();
    }
}
