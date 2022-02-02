package com.geekbrains.isemenov.spring.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.geekbrains.isemenov.spring.web.dto.Cart;
import com.geekbrains.isemenov.spring.web.dto.ProductDto;
import com.geekbrains.isemenov.spring.web.dto.StringResponse;
import com.geekbrains.isemenov.spring.web.services.CartService;
import com.geekbrains.isemenov.spring.web.services.ProductsService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartsController {
    private final CartService cartService;
    private final ProductsService productsService;

    @GetMapping("/{uuid}")
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        return cartService.getCurrentCart(getCurrentCartUuid(username, uuid));
// метод, когда запрашивает текущую корзину требует имя корзины, которым может быть username, или uuid
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(Principal principal, @PathVariable String uuid, @PathVariable Long productId) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(Principal principal, @PathVariable String uuid) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(Principal principal, @PathVariable String uuid) {
        String username = null;
        if (principal != null) {
            username = principal.getName();
        }
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
//            если имя пользователя задано, то его мы и берем в качестве основы имени корзины
        }
        return cartService.getCartUuidFromSuffix(uuid);
        // uuid в качестве основы имени корзины берем только если username == null (не авторизован)
    }
}
