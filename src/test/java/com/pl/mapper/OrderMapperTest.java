package com.pl.mapper;

import com.pl.auth.Role;
import com.pl.model.*;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.AddressRepository;
import com.pl.repository.DishRepository;
import com.pl.repository.RestaurantRepository;
import com.pl.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    private Order order1;
    private Order order2;
    private Order order3;
    private Dish dish1;
    private Dish dish2;
    private User user;
    private Address address;
    private Restaurant restaurant;

    @BeforeEach
    void testData() {
        restaurant = new Restaurant("name");
        dish1 = new Dish("name1", "description1");
        dish2 = new Dish("name2", "description2");
        user = new User("Jan", "Kowalski", "bartosz@gmail.com", "zaq1@WSX", Role.USER);
        order1 = new Order();
        address = new Address("12", "street", "city", "64-100");
        order1 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );
        order2 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );
        order3 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderMapper = new OrderMapper(userRepository, dishRepository, addressRepository, restaurantRepository);
    }

    @Test
    void testMapToOrderWhileCreatingOrder() {
        //Given
        OrderCreateDTO orderDTOtest = new OrderCreateDTO(
                1L, List.of(1L, 2L), 1L, 1L);

        User mockUser = new User();
        Dish mockDish1 = new Dish("name1", "description1");
        Dish mockDish2 = new Dish("name2", "description2");
        Address mockAddress = new Address();
        Restaurant mockRestaurant = new Restaurant();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(mockDish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(mockDish2));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(mockAddress));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mockRestaurant));
        //When
        Order mappedOrder = orderMapper.mapToOrder(orderDTOtest);
        //Then
        assertEquals(mockUser, mappedOrder.getUser());
        assertEquals(2, mappedOrder.getDishes().size());
        assertEquals(mockAddress, mappedOrder.getDeliveryAddress());
        assertEquals(mockRestaurant, mappedOrder.getRestaurant());
    }

    @Test
    void shouldMapToDto() {
        //Given
        //When
        OrderDTO attemptOrderDto = orderMapper.mapToOrderDto(order1);
        //Then
        assertEquals(OrderDTO.class, attemptOrderDto.getClass());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        List<Order> orders = List.of(order1, order2, order3);
        //When
        List<OrderDTO> attemptList = orderMapper.mapToListDto(orders);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(OrderDTO.class, attemptList.get(0).getClass());
    }
}