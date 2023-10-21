package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.Order;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.AddressRepository;
import com.pl.repository.DishRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final AddressRepository addressRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderMapper(UserRepository userRepository, DishRepository dishRepository,
                       AddressRepository addressRepository,
                       RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public Order mapToOrder(OrderCreateDTO rderCreateDTO) {
        Order order = new Order();
        order.setUser(userRepository.findById(rderCreateDTO.userId()).orElse(null));
        order.setDishSet(rderCreateDTO.dishIds().stream()
                .map(dishId -> dishRepository.findById(dishId).orElse(null))
                .collect(Collectors.toList()));
        order.setDeliveryAddress(addressRepository.findById(rderCreateDTO.deliveryAddressId()).orElse(null));
        order.setRestaurant(restaurantRepository.findById(rderCreateDTO.restaurantId()).orElse(null));

        return order;
    }

    public OrderDTO mapToOrderDto(Order order) {
        return new OrderDTO(
                order.getOrderTime(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getUser().getId(),
                order.getDishSet().stream()
                        .map(Dish::getId)
                        .collect(Collectors.toSet()),
                order.getDeliveryAddress().getId(),
                order.getRestaurant().getId()

        );
    }

    public List<OrderDTO> mapToListDto(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .toList();
    }
}
