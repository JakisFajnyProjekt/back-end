package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.mapper.OrderMapper;
import com.pl.model.*;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends AbstractService<OrderRepository, Order> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, RestaurantRepository restaurantRepository, UserRepository userRepository, DishRepository dishRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public OrderDTO create(OrderCreateDTO createOrder) {
        if (presenceCheck(createOrder)) {
            Order order = orderMapper.mapToOrder(createOrder);
            order.setOrderTime(LocalDateTime.now());
            order.setTotalPrice(calculateTotalPrice(createOrder.dishIds()));
            Order savedOrder = orderRepository.save(order);
            LOGGER.info("Order are created");
            return orderMapper.mapToOrderDto(savedOrder);
        } else {
            LOGGER.error("Something went wrong");
            throw new RuntimeException();
        }
    }

    private boolean presenceCheck(OrderCreateDTO createOrder) {
        userRepository.findById(createOrder.userId())
               .orElseThrow(() -> new NotFoundException("User Not Found"));
        restaurantRepository.findById(createOrder.restaurantId())
               .orElseThrow(() -> new NotFoundException("RestaurantNotFound"));
        addressRepository.findById(createOrder.deliveryAddressId())
               .orElseThrow(() -> new NotFoundException("Address not found"));
        LOGGER.info("presence checked");
        return true;
    }

    private BigDecimal calculateTotalPrice(List<Long> dishes) {
        if (dishes.isEmpty()){
            LOGGER.error("list are empty");
            throw new NotFoundException("You have not ordered anything");
        }
        LOGGER.info("Total price are summed");
        return dishes.stream()
                .map(dishRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public List<OrderDTO> list() {
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
