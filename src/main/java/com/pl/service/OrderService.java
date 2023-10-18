package com.pl.service;

import com.pl.exception.InvalidValuesException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.OrderMapper;
import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.OrderRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends AbstractService<OrderRepository, Order> {

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
                Order newOrder = new Order();
                newOrder.setPrice(new BigDecimal(update.get("price").toString()));
                newOrder.setIsCompleted(Boolean.parseBoolean(update.get("isCompleted").toString()));
                newOrder.setRestaurant(
                            restaurantRepository
                                .findById( Long.parseLong(update.get("restaurantId").toString() ))
                                .orElseThrow(() -> new NotFoundException("Restaurant not found"))
                );
                newOrder.setUser(
                        userRepository
                                .findById( Long.parseLong(update.get("userId").toString() ))
                                .orElseThrow(() -> new NotFoundException("User not found")));
                return orderMapper.mapToOrderDto(orderRepository.save(newOrder));
        } else {
            throw new InvalidValuesException("Provided keys are incorrect");
        }
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
