package com.pl.controller;

import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;
import com.pl.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public OrderDTO createOrder(@RequestBody Map<String,Object> order) {
        return orderService.createOrder(order);
    }
}
