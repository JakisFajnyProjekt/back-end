package com.pl.controller;

import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public OrderDTO findById(@PathVariable long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping()
    public List<OrderDTO> list() {
        return orderService.list();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO create(@Valid @RequestBody OrderCreateDTO order) {
        return orderService.create(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> remove(@PathVariable long orderId) {
        orderService.remove(orderId);
        return ResponseEntity.ok().build();
    }
}
