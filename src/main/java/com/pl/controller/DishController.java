package com.pl.controller;

import com.pl.model.dto.DishCreateDTO;
import com.pl.model.dto.DishDTO;
import com.pl.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {


    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{dishId}")
    public DishDTO findById(@PathVariable long dishId) {
        return dishService.getDishById(dishId);
    }

    @GetMapping()
    public List<DishDTO> list() {
        return dishService.listDishes();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DishDTO create(@RequestBody @Valid DishCreateDTO dish) {
        return dishService.createDish(dish);
    }

    @DeleteMapping("/{dishId}")
    public void remove(@PathVariable long dishId) {
        dishService.removeDish(dishId);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishDTO> edit(@PathVariable long dishId, @RequestBody DishDTO dish) {
        dishService.editDish(dishId, dish);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}

