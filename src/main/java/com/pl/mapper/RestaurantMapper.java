package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.Order;
import com.pl.model.Restaurant;
import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.model.dto.RestaurantDTO;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {
    public Restaurant mapToRestaurant(RestaurantDTO restaurantDto) {
        return new Restaurant(restaurantDto.name());
    }

    public RestaurantDTO mapToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDTO(restaurant.getName(),
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
