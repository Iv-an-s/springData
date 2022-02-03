package com.geekbrains.isemenov.spring.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//для возвращения токена клиенту
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
