package com.geekbrains.isemenov.spring.web.core.exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationException extends RuntimeException{

    public ValidationException(List<String> errorFieldsMessages) {
        super(errorFieldsMessages.stream().collect(Collectors.joining(", ")));
    }
}
