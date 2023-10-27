package com.pl.model.dto;

import java.math.BigDecimal;

public record DishDTO(
        String name,
        String description,
        BigDecimal price,
        Long restaurantId
) {
}