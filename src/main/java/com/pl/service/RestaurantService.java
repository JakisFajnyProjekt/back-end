package com.pl.service;


import com.pl.exception.NotFoundException;
import com.pl.mapper.RestaurantMapper;
import com.pl.model.Address;
import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.AddressRepository;
import com.pl.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RestaurantService extends AbstractService<RestaurantRepository, Restaurant> {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final AddressRepository addressRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, AddressRepository addressRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.addressRepository = addressRepository;
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

    @Transactional
    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        try {
            String name = Objects.requireNonNull(restaurantDTO.name(),
                    "restaurant name cannot be null");
            Optional<Address> address = Objects.requireNonNull(addressRepository.findById(restaurantDTO.restaurantAddress()),
                    "address id cannot be null or wrong");
            if (address.isPresent()) {
                restaurant.setName(name);
                restaurant.setAddress(address.get());
            }
            Restaurant save = restaurantRepository.save(restaurant);
            LOGGER.info("Restaurant successfully created with id " + restaurantDTO.restaurantAddress());
            return restaurantMapper.mapToRestaurantDto(save);
        } catch (Exception n) {
            LOGGER.error("wrong address id");
            throw new NotFoundException("Address not found");
        }
    }

}
