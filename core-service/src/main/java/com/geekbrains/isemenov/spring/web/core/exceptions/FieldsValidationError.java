package com.geekbrains.isemenov.spring.web.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldsValidationError {
    private List<String> errorFieldsMessages;
}
