package com.geekbrains.isemenov.spring.web.cart;

import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootTest
//@ActiveProfiles("test")
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private RestTemplate restTemplate;

    private static ProductDto productDtoBread;
    private static ProductDto productDtoMilk;

    @BeforeAll
    public static void initProducts() {
        productDtoBread = new ProductDto(1L, "Bread", BigDecimal.valueOf(50), "bakery_products");
        productDtoMilk = new ProductDto(2L, "Milk", BigDecimal.valueOf(100), "dairy_products");
    }

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoBread.getId());
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(150, cartService.getCurrentCart("test_cart").getTotalPrice());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(350, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void clearCartTest() {
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        cartService.clearCart("test_cart");
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void removeItemFromCartTest() {
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        cartService.removeItemFromCart("test_cart", productDtoBread.getId());
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        cartService.removeItemFromCart("test_cart", productDtoMilk.getId());
        Assertions.assertTrue(cartService.getCurrentCart("test_cart").getItems().isEmpty());
    }

    @Test
    public void decrementItemTest() {
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoBread.getId());
        cartService.addToCart("test_cart", productDtoBread.getId());
        Assertions.assertEquals(3, cartService.getCurrentCart("test_cart").getItems().get(0).getQuantity());
        cartService.decrementItem("test_cart", productDtoBread.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().get(0).getQuantity());
    }

    @Test
    public void mergeTest() {
        cartService.clearCart("user_cart");
        Mockito.doReturn(productDtoBread).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        Mockito.doReturn(productDtoMilk).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        cartService.addToCart("user_cart", productDtoBread.getId());
        cartService.addToCart("user_cart", productDtoBread.getId());
        Assertions.assertEquals(1, cartService.getCurrentCart("user_cart").getItems().size());
        Assertions.assertEquals(100*100, cartService.getCurrentCart("user_cart").getItems().stream().mapToInt(p -> p.getPrice().multiply(BigDecimal.valueOf(100)).intValue()).sum());
        cartService.addToCart("test_cart", productDtoMilk.getId());
        cartService.addToCart("test_cart", productDtoBread.getId());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(150*100, cartService.getCurrentCart("test_cart").getItems().stream().mapToInt(p -> p.getPrice().multiply(BigDecimal.valueOf(100)).intValue()).sum());
        cartService.merge("user_cart", "test_cart");
        Assertions.assertEquals(2, cartService.getCurrentCart("user_cart").getItems().size());
        Assertions.assertEquals(250*100, cartService.getCurrentCart("user_cart").getItems().stream().mapToInt(p -> p.getPrice().multiply(BigDecimal.valueOf(100)).intValue()).sum());
    }
}
