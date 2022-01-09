package ru.isemenov.springData.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isemenov.springData.dto.OrderDto;
import ru.isemenov.springData.dto.OrderInfoDto;
import ru.isemenov.springData.services.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderInfoDto orderInfoDto, Principal principal){
        OrderDto orderDto = orderService.createOrder(orderInfoDto, principal.getName());
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}
