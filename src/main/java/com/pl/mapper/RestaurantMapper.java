package com.pl.mapper;

import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {
    public Restaurant mapToRestaurant(RestaurantDTO restaurantDTO) {
        return new Restaurant(
                restaurantDTO.id(),
                restaurantDTO.name(),
                restaurantDTO.address()
        );
    }

    public RestaurantDTO mapToRestaurantDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress()
        );
    }

    public List<RestaurantDTO> mapToListDTO(final List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::mapToRestaurantDTO)
                .toList();
    }

}
