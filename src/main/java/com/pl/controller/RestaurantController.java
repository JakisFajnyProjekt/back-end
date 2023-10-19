package com.pl.controller;

import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("")
    public List<RestaurantDTO> list() {
        return restaurantService.list();
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO findById(@PathVariable long restaurantId) {
        return restaurantService.findById(restaurantId);
    }
}
