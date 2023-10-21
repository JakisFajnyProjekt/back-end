package com.pl.service;

import com.pl.mapper.OrderMapper;
import com.pl.model.Dish;
import com.pl.model.Order;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.DishRepository;
import com.pl.repository.OrderRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService extends AbstractService<OrderRepository, Order> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, RestaurantRepository restaurantRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderCreateDTO createOrder) {
        Order order = orderMapper.mapToOrder(createOrder);
        order.setTotalPrice(calculateTotalPrice(createOrder.dishIds()));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToOrderDto(savedOrder);
    }

    public BigDecimal calculateTotalPrice(Set<Long> dishes) {
        return dishes.stream()
                .map(dishRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public List<OrderDTO> listOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            LOGGER.info("the list of orders are empty");
            return new ArrayList<>();
        }
        return orderMapper.mapToListDto(orders);
    }

    public OrderDTO getOrderById(long orderId) {
        Order order = findEntity(orderRepository, orderId);
        LOGGER.info("Order found with id" + orderId);
        return orderMapper.mapToOrderDto(order);
    }


    @Transactional
    public OrderDTO remove(long orderId) {
        Order order = findEntity(orderRepository, orderId);
        orderRepository.delete(order);
        LOGGER.info("Order with id " + order + " deleted");

        return orderMapper.mapToOrderDto(order);
    }

}
