package com.pl.mapper;

import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {
    public Restaurant mapToRestaurant(RestaurantDTO restaurantDto) {
        return new Restaurant(
                restaurantDto.name(),
                restaurantDto.address()
        );
    }

    public RestaurantDTO mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getName(),
                restaurant.getAddress()
        );
    }

    public List<RestaurantDTO> mapToListDto(final List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::mapToRestaurantDto)
                .toList();
    }

}
