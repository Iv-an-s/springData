package ru.isemenov.springData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//для возвращения токена клиенту
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
