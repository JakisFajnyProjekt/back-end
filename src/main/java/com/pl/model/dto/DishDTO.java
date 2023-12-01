package com.pl.model.dto;

import com.pl.model.Category;

import java.math.BigDecimal;

public record DishDTO(
        Long dishId,
        String name,
        String description,
        BigDecimal price,
        Long restaurantId,
        Category category
) {
}