package com.pl.mapper;

import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        restaurant = new Restaurant("Luigi");
        restaurantDto = new RestaurantDTO("Luigi");
        expectedDto = new RestaurantDTO("Luigi");

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
}
