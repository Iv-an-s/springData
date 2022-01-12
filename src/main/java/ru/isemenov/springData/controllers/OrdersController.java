package ru.isemenov.springData.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.isemenov.springData.converters.OrderConverter;
import ru.isemenov.springData.dto.OrderDetailsDto;
import ru.isemenov.springData.dto.OrderDto;
import ru.isemenov.springData.entities.Order;
import ru.isemenov.springData.entities.User;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;
import ru.isemenov.springData.services.OrderService;
import ru.isemenov.springData.services.UserService;

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
