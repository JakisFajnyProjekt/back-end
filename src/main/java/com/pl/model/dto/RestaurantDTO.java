package com.pl.model.dto;

public record RestaurantDTO(
        Long restaurantId,
        String name,
        Long restaurantAddress
) {
}
