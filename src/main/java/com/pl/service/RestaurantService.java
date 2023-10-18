package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.mapper.RestaurantMapper;
import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService extends AbstractService<RestaurantRepository, Restaurant> {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }


    public List<RestaurantDTO> list() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            LOGGER.info("the list of restaurants are empty");
            return new ArrayList<>();
        }
        return restaurantMapper.mapToListDto(restaurants);
    }

    public RestaurantDTO findById(long restaurantId) {
        Restaurant restaurant = findEntity(restaurantRepository, restaurantId);
        LOGGER.info("User found with id" + restaurantId);
        return restaurantMapper.mapToRestaurantDto(restaurant);
    }

}
