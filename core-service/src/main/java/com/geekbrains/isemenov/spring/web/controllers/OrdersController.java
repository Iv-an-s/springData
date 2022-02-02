package com.geekbrains.isemenov.spring.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.isemenov.spring.web.converters.OrderConverter;
import com.geekbrains.isemenov.spring.web.dto.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.dto.OrderDto;
import com.geekbrains.isemenov.spring.web.entities.Order;
import com.geekbrains.isemenov.spring.web.entities.User;
import com.geekbrains.isemenov.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.services.OrderService;
import com.geekbrains.isemenov.spring.web.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto orderDetailsDto, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailsDto);
        //return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal){
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter :: entityToDto)
                .collect(Collectors.toList());
    }
}
