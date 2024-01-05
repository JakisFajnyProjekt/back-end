package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.Restaurant;
import com.pl.model.dto.DishCreateDTO;
import com.pl.model.dto.DishDTO;
import com.pl.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DishMapperTest {
    private Dish dish;
    private DishCreateDTO dishDto;
    private DishDTO expectedDto;
    private Restaurant restaurant;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void testData() {
        restaurant = new Restaurant("restaurant");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        dish = new Dish("Pizza", "This is very good pizza!", new BigDecimal(30), restaurant, Dish.Category.APPETIZER);
        dishDto = new DishCreateDTO("Pizza", "This is very good pizza!", new BigDecimal(30), savedRestaurant.getId(), Dish.Category.BREAKFAST);
        expectedDto = new DishDTO(2L, "Pizza", "This is very good pizza!", new BigDecimal(30), savedRestaurant.getId(), Dish.Category.APPETIZER);
    }

    @Test
    void shouldMapToDto() {
        //Given
        //When
        DishDTO attemptDishDto = dishMapper.mapToDishDto(dish);
        //Then
        assertEquals(expectedDto.name(), attemptDishDto.name());
        assertEquals(expectedDto.description(), attemptDishDto.description());
    }

    @Test
    void shouldMapFromDto() {
        //Given
        //When
        Dish attemptDish = dishMapper.mapToDish(dishDto);
        //Then
        assertEquals(dish.getName(), attemptDish.getName());
        assertEquals(dish.getDescription(), attemptDish.getDescription());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        List<Dish> dishes = List.of(
                new Dish("Pizza", "This is very good pizza", new BigDecimal(30), restaurant, Dish.Category.APPETIZER),
                new Dish("Burger", "Delicious burger", new BigDecimal(30), restaurant, Dish.Category.APPETIZER),
                new Dish("Pasta", "Homemade pasta", new BigDecimal(30), restaurant, Dish.Category.APPETIZER)
        );
        //When
        List<DishDTO> attemptList = dishMapper.mapToListDto(dishes);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(DishDTO.class, attemptList.get(0).getClass());
    }
}
