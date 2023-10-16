package com.pl.controller;

import com.pl.model.dto.DishDTO;
import com.pl.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        public List<DishDTO> listDishes() {
            return dishService.listDishes();
        }

        @DeleteMapping("/{dishId}")
        public void removeDish(@PathVariable long dishId) {
            dishService.removeDish(dishId);
        }

        @PutMapping("/{dishId}")
        public ResponseEntity<DishDTO> editDish(@PathVariable long dishId, @RequestBody DishDTO updates) {
            dishService.editDish(dishId, updates);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
}

