package com.pl.model.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record OrderCreateDTO(
        @NotNull(message = "userId require")
        @Min(value = 1, message = "Delivery address ID must be greater than or equal to 1")
        @Positive
        Long userId,
        @NotNull(message = "dish require")
        List<Long> dishIds,
        @NotNull(message = "delivery address required")
        Long deliveryAddressId,
        @NotNull(message = "restaurant required")
        Long restaurantId
) {
}
