package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.mapper.OrderMapper;
import com.pl.model.Dish;
import com.pl.model.Order;
import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "orderList", allEntries = true)
    public OrderDTO create(OrderCreateDTO createOrder) {
        if (presenceCheckForOrder(createOrder)) {
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

    @Transactional
    public boolean presenceCheckForOrder(OrderCreateDTO createOrder) {
        userRepository.findById(createOrder.userId())
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        restaurantRepository.findById(createOrder.restaurantId())
                .orElseThrow(() -> new NotFoundException("Restaurant Not Found"));
        addressRepository.findById(createOrder.deliveryAddressId())
                .orElseThrow(() -> new NotFoundException("Address Not found"));
        LOGGER.info("presence checked");
        return true;
    }

    private BigDecimal calculateTotalPrice(List<Long> dishes) {
        if (dishes.isEmpty()) {
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

    @Transactional
    @Cacheable(cacheNames = "orderList")
    public List<OrderDTO> list() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            LOGGER.info("the list of orders are empty");
            return new ArrayList<>();
        }
        LOGGER.info(orders.size() + " orders found");
        return orderMapper.mapToListDto(orders);
    }

    @Transactional
    @Cacheable(cacheNames = "orderList", key = "#orderId")
    public OrderDTO getOrderById(long orderId) {
        Order order = findEntity(orderRepository, orderId);
        LOGGER.info("Order found with id" + orderId);
        return orderMapper.mapToOrderDto(order);
    }


    @Transactional
    @CacheEvict(value = "orderList", allEntries = true)
    public void remove(long orderId) {
        Order order = findEntity(orderRepository, orderId);
        orderRepository.delete(order);
        LOGGER.info("Order with id " + orderId + " deleted");
    }
}
