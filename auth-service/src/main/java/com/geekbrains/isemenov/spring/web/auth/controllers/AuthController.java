package com.geekbrains.isemenov.spring.web.auth.controllers;

import com.geekbrains.isemenov.spring.web.api.exceptions.AppError;
import com.geekbrains.isemenov.spring.web.auth.dto.JwtRequest;
import com.geekbrains.isemenov.spring.web.auth.dto.JwtResponse;
import com.geekbrains.isemenov.spring.web.auth.services.UserService;
import com.geekbrains.isemenov.spring.web.auth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//Задача контроллера - получить от клиента логин/пароль и вернуть ему токен

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    //AuthenticationManager - часть Spring Security, умеет по логину и паролю проверять есть ли такой пользователь.

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException ex){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
