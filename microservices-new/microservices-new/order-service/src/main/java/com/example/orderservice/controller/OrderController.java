package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequestDtoIn;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDtoIn orderRequestDtoIn) {
        orderService.placeOrder(orderRequestDtoIn);
        return "Order placed successfully";
    }

}
