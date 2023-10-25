package com.pl.model.dto;

import com.pl.model.Dish;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record OrderCreateDTO(
        @NotNull(message = "userId required")
        Long userId,
        @NotNull(message = "dish required")
        List<Long> dishIds,
        @NotNull(message = "delivery address required")
        Long deliveryAddressId,
        @NotNull(message = "restaurant required")
        Long restaurantId
) {
}
