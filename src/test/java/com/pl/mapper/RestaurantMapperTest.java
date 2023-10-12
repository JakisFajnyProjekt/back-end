package com.pl.mapper;

import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RestaurantMapperTest {

    @InjectMocks
    RestaurantMapper restaurantMapper;

    Restaurant restaurant;
    RestaurantDTO restaurantDto;
    RestaurantDTO expectedDto;

    @BeforeEach
    void testData(){
        restaurant = new Restaurant("Luigi", "Mokotów 17");
        restaurantDto = new RestaurantDTO("Luigi", "Mokotów 17");
        expectedDto = new RestaurantDTO("Luigi", "Mokotów 17");
    }
    @Test
    void shouldMapToDto() {
        //Given
        //When
        RestaurantDTO attemptRestaurantDto = restaurantMapper.mapToRestaurantDto(restaurant);
        //Then
        assertEquals(expectedDto.name(), attemptRestaurantDto.name());
        assertEquals(expectedDto.address(), attemptRestaurantDto.address());
    }
    @Test
    void shouldMapFromDto() {
        //Given
        //When
        Restaurant attemptRestaurant = restaurantMapper.mapToRestaurant(restaurantDto);
        //Then
        assertEquals(expectedDto.name(), attemptRestaurant.getName());
        assertEquals(expectedDto.address(), attemptRestaurant.getAddress());
    }
    @Test
    void shouldMapToListDto() {
        //Given
        List<Restaurant> restaurants = List.of(
                new Restaurant("Luigi", "Mokotów 17"),
                new Restaurant("Luigi", "Mokotów 17"),
                new Restaurant("Luigi", "Mokotów 17")
        );
        //When
        List<RestaurantDTO> attemptList = restaurantMapper.mapToListDto(restaurants);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(RestaurantDTO.class, attemptList.get(0).getClass());
    }
}
