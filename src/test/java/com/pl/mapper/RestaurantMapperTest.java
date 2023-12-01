package com.pl.mapper;

import com.pl.auth.Role;
import com.pl.model.*;
import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.RestaurantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RestaurantMapperTest {

    @Autowired
    private RestaurantMapper restaurantMapper;
    private Restaurant restaurant;
    private RestaurantDTO restaurantDto;
    private RestaurantDTO expectedDto;

    @BeforeEach
    void testData() {
        restaurant = new Restaurant("Luigi", new Address());
        restaurantDto = new RestaurantDTO(1L,"Luigi", 1L);
        expectedDto = new RestaurantDTO(2L,"Luigi", 1L);

    }

    @Test
    void shouldMapToDto() {
        //Given
        //When
        RestaurantDTO attemptRestaurantDto = restaurantMapper.mapToRestaurantDto(restaurant);
        //Then
        assertEquals(RestaurantDTO.class, attemptRestaurantDto.getClass());
        assertEquals(expectedDto.name(), attemptRestaurantDto.name());

    }

    @Test
    void shouldMapFromDto() {
        //Given
        //When
        Restaurant attemptRestaurant = restaurantMapper.mapToRestaurant(restaurantDto);
        //Then
        assertEquals(Restaurant.class, attemptRestaurant.getClass());
        assertEquals(expectedDto.name(), attemptRestaurant.getName());

    }

    @Test
    void shouldMapToListDto() {
        //Given
        List<Restaurant> restaurants = List.of(restaurant

        );
        //When
        List<RestaurantDTO> attemptList = restaurantMapper.mapToListDto(restaurants);
        //Then
        assertEquals(1, attemptList.size());
        assertEquals(RestaurantDTO.class, attemptList.get(0).getClass());
    }

    @Test
    void shouldMapToDtoForOrderList(){
        //Given
        restaurant = new Restaurant("name");
        Dish dish1 = new Dish("name1", "description1");
        Dish dish2 = new Dish("name2", "description2");
        User user = new User("Jan", "Kowalski", "bartosz@gmail.com", "zaq1@WSX", Role.USER);

        Address address = new Address("12", "street", "city", "64-100");
        Order order1 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );
       Order order2 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );
        Order order3 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", user, List.of(dish1, dish2), address, restaurant
        );

        List<Order> orders = List.of(order1, order2, order3);


        //When
        List<OrderByRestaurantDTO> orderByRestaurantDTOS = restaurantMapper.mapToDtoForOrderList(orders);

        //Then
        assertEquals(OrderByRestaurantDTO.class,orderByRestaurantDTOS.get(0).getClass());
        assertEquals(OrderByRestaurantDTO.class,orderByRestaurantDTOS.get(1).getClass());
        assertEquals(OrderByRestaurantDTO.class,orderByRestaurantDTOS.get(2).getClass());
    }
}
