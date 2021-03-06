package com.geekbrains.isemenov.spring.web.cart.exceptions;

import com.geekbrains.isemenov.spring.web.api.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchNoConnectionWithServiceException(NoConnectionWithServiceException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CORE_SERVICE_DISABLED.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchCartIsBrokenException(CartIsBrokenException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
