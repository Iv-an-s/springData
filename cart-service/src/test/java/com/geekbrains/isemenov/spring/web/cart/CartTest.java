package com.geekbrains.isemenov.spring.web.cart;

import com.geekbrains.isemenov.spring.web.cart.models.Cart;
import com.geekbrains.isemenov.spring.web.cart.models.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartTest {
    @Autowired
    private Cart cart;
    @MockBean
    private CartItem cartItem;



}
