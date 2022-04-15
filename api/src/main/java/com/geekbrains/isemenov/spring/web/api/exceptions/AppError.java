package com.geekbrains.isemenov.spring.web.api.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Модель ошибки")
public class AppError {
    private String code;
    private String message;
    private final Date date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public AppError() {
        this.date = new Date();
    }

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
        this.date = new Date();
    }
}
