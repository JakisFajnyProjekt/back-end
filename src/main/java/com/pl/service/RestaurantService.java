package com.pl.service;


import com.pl.exception.NotFoundException;
import com.pl.mapper.RestaurantMapper;
import com.pl.model.Address;
import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.AddressRepository;
import com.pl.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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


    @Cacheable(cacheNames = "restaurantsList")
    public List<RestaurantDTO> list() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            LOGGER.info("the list of restaurants are empty");
            return new ArrayList<>();
        }
        LOGGER.info(restaurants.size() + " restaurants found");
        return restaurantMapper.mapToListDto(restaurants);
    }

    @Cacheable(cacheNames = "restaurantsList", key = "#restaurantId")
    public RestaurantDTO findById(long restaurantId) {
        Restaurant restaurant = findEntity(restaurantRepository, restaurantId);
        LOGGER.info("restaurant found with id " + restaurantId);
        return restaurantMapper.mapToRestaurantDto(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurantsList", allEntries = true)
    public RestaurantDTO create(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        try {
            restaurantObjCheck(restaurantDTO, restaurant);
            Restaurant save = restaurantRepository.save(restaurant);
            LOGGER.info("Restaurant successfully created with id " + restaurantDTO.restaurantAddress());
            return restaurantMapper.mapToRestaurantDto(save);
        } catch (Exception n) {
            LOGGER.error("wrong address id");
            throw new NotFoundException("Address not found");
        }
    }

    private void restaurantObjCheck(RestaurantDTO restaurantDTO, Restaurant restaurant) {
        String name = Objects.requireNonNull(restaurantDTO.name(),
                "restaurant name cannot be null");
        Optional<Address> address = Objects.requireNonNull(addressRepository.findById(restaurantDTO.restaurantAddress()),
                "address id cannot be null or wrong");
        if (address.isPresent()) {
            restaurant.setName(name);
            restaurant.setAddress(address.get());
        }
    }

}
