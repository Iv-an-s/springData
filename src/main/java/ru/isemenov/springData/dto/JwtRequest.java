package ru.isemenov.springData.dto;

import lombok.Data;
//используется когда пользователь хочет прислать нам логин и пароль в обмен на токен
@Data
public class JwtRequest {
    private String username;
    private String password;
}
