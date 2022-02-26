package com.geekbrains.isemenov.spring.web.cart.services;


import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartItemDto;
import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.api.exceptions.AppError;
import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.cart.integrations.ProductsServiceIntegration;
import com.geekbrains.isemenov.spring.web.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsServiceIntegration productsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    private List<CartItemDto> productDayCounter = new ArrayList<>();

    public AnalyticsDto getDailyProductsAnalytics(){
        List<CartItemDto> productCounterCopy = new ArrayList<>();
        productDayCounter.sort(new Comparator<CartItemDto>() {
            @Override
            public int compare(CartItemDto o1, CartItemDto o2) {
                return o1.getQuantity()-o2.getQuantity();
            }
        });
        if(productDayCounter.size()<=5) {
            productCounterCopy.addAll(productDayCounter);
        }else {
            for (int i = 0; i < 5; i++) {
                productCounterCopy.add(productDayCounter.get(i));
            }
        }
        productDayCounter.clear();

//        productCounterCopy.add(new CartItemDto(1L, "Milk", 2, 200, 400));
//        productCounterCopy.add(new CartItemDto(2L, "Milk2", 2, 300, 400));
//        productCounterCopy.add(new CartItemDto(3L, "Milk3", 2, 400, 400));
//        productCounterCopy.add(new CartItemDto(4L, "Milk4", 2, 500, 400));
//        productCounterCopy.add(new CartItemDto(5L, "Milk5", 2, 600, 400));

        return new AnalyticsDto(productCounterCopy);
    }

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    // генерация имени корзины для гостей
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
            //ops - выполнение какой-то операции для значения
            //opsForValue().set() - хотим в Redis записать какой-то объект
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        // GET from REDIS
        // UPDATE OBJECT
        // SET TO REDIS
        ProductDto productDto;
        if (productId.equals(5L)){
            throw new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найден, id: ");
        }else {
            productDto = productsServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найден, id: " + productId));
        }
        execute(cartKey, c -> {
            c.add(productDto);
        });
        if(!productDayCounter.isEmpty()) {
            boolean isFounded = false;
            for (int i = 0; i < productDayCounter.size(); i++) {
                if (productDayCounter.get(i).getProductId().equals(productDto.getId())) {
                    productDayCounter.get(i).setQuantity(productDayCounter.get(i).getQuantity() + 1);
                    isFounded = true;
                    break;
                }
            }
            if(!isFounded) {
                productDayCounter.add(new CartItemDto(productDto.getId(), productDto.getTitle(), 1, productDto.getPrice(), productDto.getPrice()));
            }
        }else productDayCounter.add(new CartItemDto(productDto.getId(), productDto.getTitle(), 1, productDto.getPrice(), productDto.getPrice()));
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    // пользователь зашел под учеткой гостя, накидал продуктов, и решил зайти под своей учеткой и оформить заказ.
    // когда пользователь с гостя захочет зайти в свою учетку, мы должны склеить две корзины вместе.
    // достаем текущую гостевую корзину -> достаем корзину User-а ->
    // -> в корзину пользователя "вмерживаем" все продукты из гостевой корзины
    // -> очищенную гостевую корзину закидываем обратно в Redis
    // -> обновленную корзину Usera c продуктами закидываем обратно в Redis
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);            // GET
        action.accept(cart);                            // UPDATE
        redisTemplate.opsForValue().set(cartKey, cart); //SET
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
