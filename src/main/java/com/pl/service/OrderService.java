package com.pl.service;

import com.pl.exception.InvalidValuesException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.OrderMapper;
import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.OrderRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Map;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    public OrderDTO createOrder(Map<String, Object> update) {
        if (update.containsKey("price") && update.containsKey("isCompleted") && update.containsKey("restaurantId") && update.containsKey("userId")) {
            try {
                Order newOrder = new Order();
                newOrder.setPrice(new BigDecimal(update.get("price").toString()));
                newOrder.setIsCompleted(Boolean.parseBoolean(update.get("isCompleted").toString()));
                newOrder.setRestaurant(
                            restaurantRepository
                                .findById( (Long) update.get("restaurantId") )
                                .orElseThrow(() -> new NotFoundException("Restaurant not found"))
                );
                newOrder.setUser(
                        userRepository
                                .findById( (Long) update.get("userId") )
                                .orElseThrow(() -> new NotFoundException("user not found")));
                return orderMapper.mapToOrderDto(orderRepository.saveOrder(newOrder));
            } catch (Exception e) {
                throw new InvalidValuesException("Provided values does not contain name and description properties");
            }
        } else {
            throw new InvalidValuesException("Missing keys in provided object");
        }
    }
}
