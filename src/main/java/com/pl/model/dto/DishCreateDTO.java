package com.pl.model.dto;

import com.pl.model.Category;

import java.math.BigDecimal;

public record DishCreateDTO(
        String name,
        String description,
        BigDecimal price,
        Long restaurantId,
        Category category
) {
}
