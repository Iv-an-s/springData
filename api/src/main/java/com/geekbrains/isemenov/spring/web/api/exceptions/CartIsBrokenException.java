package com.geekbrains.isemenov.spring.web.api.exceptions;

public class CartIsBrokenException extends RuntimeException{
    public CartIsBrokenException(String message) {
        super(message);
    }
}
