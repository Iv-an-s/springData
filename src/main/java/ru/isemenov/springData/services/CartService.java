package ru.isemenov.springData.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.dto.Cart;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

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
        Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            c.add(product);
        });
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
