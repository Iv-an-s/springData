package ru.isemenov.springData.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public void createOrder(@RequestBody OrderInfoDto orderInfoDto, Principal principal) {
        orderService.createOrder(orderInfoDto, principal.getName());
        //return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}
