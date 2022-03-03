package com.geekbrains.isemenov.spring.web.api.exceptions;

import com.geekbrains.isemenov.spring.web.api.exceptions.AppError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Schema(description = "ошибка корзины")
public class CartServiceAppError extends AppError {
    @Schema(description = "константы - коды ошибок")
    public enum CartServiceErrors{
        CART_IS_BROKEN, CART_ID_GENERATOR_DISABLED, CART_NOT_FOUND, CORE_SERVICE_DISABLED
    }

    public CartServiceAppError(String code, String message) {
        super(code, message);
    }
}
