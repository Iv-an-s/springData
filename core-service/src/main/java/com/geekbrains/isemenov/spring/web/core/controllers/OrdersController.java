package com.geekbrains.isemenov.spring.web.core.controllers;

import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.isemenov.spring.web.core.converters.OrderConverter;
import com.geekbrains.isemenov.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.isemenov.spring.web.api.core.OrderDto;
import com.geekbrains.isemenov.spring.web.core.services.OrderService;

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

    @GetMapping("/analytics")
    public AnalyticsDto getMonthProductsAnalytics() {
        return orderService.getMonthProductsAnalytics();
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }
}
