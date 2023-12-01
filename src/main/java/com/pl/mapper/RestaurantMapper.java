package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.Order;
import com.pl.model.Restaurant;
import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.RestaurantCreateDTO;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.AddressRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {

    private final AddressRepository addressRepository;

    public RestaurantMapper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Restaurant mapToRestaurant(RestaurantCreateDTO restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.name());
        restaurant.setAddress(addressRepository.findById(restaurantDto.restaurantAddress()).orElse(null));
        return restaurant;
    }

    public RestaurantCreateDTO mapToRestaurantCreateDto(Restaurant restaurant) {
        return new RestaurantCreateDTO(
                restaurant.getName(),
                restaurant.getAddress().getId());
    }

    public RestaurantDTO mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress().getId());
    }




    public List<OrderByRestaurantDTO> mapToDtoForOrderList(final List<Order> orders){
        return orders.stream()
                .map(order -> new OrderByRestaurantDTO(
                        order.getOrderTime(),
                        order.getTotalPrice(),
                        order.getId(),
                        order.getDishes().stream()
                                .map(Dish::getId)
                                .toList(),
                        order.getDeliveryAddress().getId()
                ))
                .toList();
    }

    public List<RestaurantDTO> mapToListDto(final List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::mapToRestaurantDto)
                .toList();
    }

}
