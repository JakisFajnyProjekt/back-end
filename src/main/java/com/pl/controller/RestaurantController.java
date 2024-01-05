package com.pl.controller;

import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.RestaurantCreateDTO;
import com.pl.model.dto.RestaurantDTO;
import com.pl.service.RestaurantService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public List<RestaurantDTO> list() {
        return restaurantService.list();
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO findById(@PathVariable long restaurantId) {
        return restaurantService.findById(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantCreateDTO addRestaurant(@RequestBody RestaurantCreateDTO restaurantDTO) {
        return restaurantService.create(restaurantDTO);
    }

    @GetMapping("orders/{restaurantId}")
    public List<OrderByRestaurantDTO> findOrders(@PathVariable long restaurantId) {
        return restaurantService.findOrders(restaurantId);
    }
}
