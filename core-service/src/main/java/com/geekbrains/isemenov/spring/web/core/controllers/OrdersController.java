package com.geekbrains.isemenov.spring.web.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.isemenov.spring.web.core.converters.OrderConverter;
import com.geekbrains.isemenov.spring.web.core.dto.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.core.dto.OrderDto;
import com.geekbrains.isemenov.spring.web.core.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
        //return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter :: entityToDto)
                .collect(Collectors.toList());
    }
}
