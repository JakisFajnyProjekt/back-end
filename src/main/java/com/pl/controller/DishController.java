package com.pl.controller;

import com.pl.model.dto.DishDTO;
import com.pl.service.DishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

        private final DishService dishService;
        public DishController(DishService dishService) {
            this.dishService = dishService;
        }

        @GetMapping("/{dishId}")
        public DishDTO findUserById(@PathVariable long dishId){
            return dishService.getDishById(dishId);
        }

        @GetMapping("")
        public List<DishDTO> getListOfAllDishes() {
            return dishService.listDishes();
        }

        @DeleteMapping("/{dishId}")
        public void removeDishById(@PathVariable long dishId) {
            dishService.removeDishById(dishId);
        }

        @PutMapping("/{dishId}")
        public void updateDishById(@PathVariable long dishId, @RequestBody Map<String, Object> updates) {
            dishService.updateDishById(dishId, updates);
        }
}

