package ru.isemenov.springData.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.dto.Cart;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private Cart cart;

    @PostConstruct
    public void init(){
        cart = new Cart();
    }

    public Cart getCurrentCart(){
        return cart;
    }

    public void addProductByIdToCart(Long productId){
        if(!getCurrentCart().addProduct(productId)){
            Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт с id:" + productId + " не найден"));
            getCurrentCart().addProduct(product);
        }
    }

    public void clear(){
        getCurrentCart().clear();
    }
}
