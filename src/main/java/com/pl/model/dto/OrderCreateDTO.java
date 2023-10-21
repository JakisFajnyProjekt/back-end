package com.pl.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateDTO(
        @NotNull(message = "userId require")
        Long userId,
        @NotNull(message = "dish require")
        List<Long> dishIds,
        @NotNull(message = "delivery addres required")
        Long deliveryAddressId,
        @NotNull(message = "restaurant required")
        Long restaurantId
) {
}
