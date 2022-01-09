package ru.isemenov.springData.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isemenov.springData.dto.Cart;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartsController {
    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart(){
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id){
        cartService.addProductByIdToCart(id);
    }

    @GetMapping("/clear")
    public void clearCart(){
        cartService.getCurrentCart().clear();
    }

}
