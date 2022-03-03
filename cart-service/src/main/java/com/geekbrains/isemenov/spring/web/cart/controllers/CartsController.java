package com.geekbrains.isemenov.spring.web.cart.controllers;


import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartDto;
import com.geekbrains.isemenov.spring.web.api.dto.StringResponse;
import com.geekbrains.isemenov.spring.web.api.exceptions.CartIsBrokenException;
import com.geekbrains.isemenov.spring.web.cart.converters.CartConverter;
import com.geekbrains.isemenov.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзины", description = "Методы работы с корзинами")
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Запрос на получение аналитики по продуктам, добавленным в корзины за день",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AnalyticsDto.class))
                    )
            }
    )
    @GetMapping("/analytics/day")
    public AnalyticsDto getDailyProductsAnalytics() {
        return cartService.getDailyProductsAnalytics();
    }

    @Operation(
            summary = "Запрос на получение корзины по uuid",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
    }
    )
    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) @Parameter(description = "username - если пользователь авторизовался") String username,
                           @PathVariable @Parameter(description = "id корзины", required = true) String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(username, uuid)));
// метод, когда запрашивает текущую корзину требует имя корзины, которым может быть username, или uuid
    }

    @Operation(
            summary = "Запрос на получение корзины для гостя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @Operation(
            summary = "Запрос на добавление продукта в корзину",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))
                    )
            })
    @GetMapping("/{uuid}/add/{productId}")
    public ResponseEntity<?> add(@RequestHeader(required = false) @Parameter(description = "username, если пользователь авторизован", required = false) String username,
                                 @PathVariable  @Parameter(description = "uuid - id корзины, если пользователь не авторизован(гость)", required = true) String uuid,
                                 @PathVariable @Parameter(description = "id добавляемого продукта") Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
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
