package com.pl.mapper;

import com.pl.model.Dish;
import com.pl.model.dto.DishDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DishMapperTest {

    private Dish dish;
    private DishDTO dishDto;
    private DishDTO expectedDto;
    @Autowired
    private DishMapper dishMapper;

    @BeforeEach
    void testData() {
        dish = new Dish(0L, "Pizza", "This is very good pizza!");
        dishDto = new DishDTO("Pizza", "This is very good pizza!");
        expectedDto = new DishDTO("Pizza", "This is very good pizza!");
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
                new Dish("Pizza", "This is very good pizza"),
                new Dish("Burger", "Delicious burger"),
                new Dish("Pasta", "Homemade pasta")
        );
        //When
        List<DishDTO> attemptList = dishMapper.mapToListDto(dishes);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(DishDTO.class, attemptList.get(0).getClass());
    }
}
