package com.ezinne.orderservice.controller;

import com.ezinne.orderservice.model.Order;
import com.ezinne.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
        return "Order placed successfully";
    }

    @DeleteMapping("/api/v1/order/{orderId}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order has been cancelled";
    }
}
