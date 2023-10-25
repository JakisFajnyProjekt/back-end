package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.model.Dish;
import com.pl.model.dto.DishDTO;
import com.pl.repository.DishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishService dishService;


    private Dish dish1;
    private Dish dish2;
    private Dish dish3;
    private DishDTO dishDTO;
    private List<Dish> dishList;


    @BeforeEach
    void dataForTests() {
        dish1 = new Dish("dish1", "descriotion1");
        dish2 = new Dish("dish2", "descriotion2");
        dish3 = new Dish("dish3", "descriotion3");
        dishDTO = new DishDTO("dish_DTO", "description_DTO");
        dishList = List.of(dish1, dish2, dish3);
    }

    @BeforeEach
    void cleanUpBefore() {
        dishRepository.deleteAll();
    }

    @AfterEach
    void cleanUpAfter() {
        dishRepository.deleteAll();
    }


    @Test
    void shouldFindUserFromDB() {
        //Give
        Dish savedDish = dishRepository.save(dish1);

        //When
        DishDTO findDishById = dishService.getDishById(savedDish.getId());

        //Thne
        String expectedName = "dish1";
        assertEquals(DishDTO.class, findDishById.getClass());
        assertEquals(expectedName, findDishById.name());
    }

    @Test
    void shouldFindListOfDishes() {
        //Give
        dishRepository.saveAll(dishList);

        //When
        List<DishDTO> dishDTOList = dishService.listDishes();

        //Then
        assertEquals(3, dishDTOList.size());
        assertEquals("dish1", dishDTOList.get(0).name());
        assertEquals("dish2", dishDTOList.get(1).name());
        assertEquals("dish3", dishDTOList.get(2).name());
    }

    @Test
    void shoudlHandleExceptionWhenDishIdIsWrong() {
        //Given
        long wrongDishID = 1231;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> dishService.getDishById(wrongDishID));

        //Thne
        String expectedMessage = "Dish not found with given id " + wrongDishID;
        assertEquals(expectedMessage, notFoundException.getMessage());
    }

    @Test
    void shoudReturnEmptyListWhenDishListAreEmpty() {
        //Give
        int emptyListOfDishes =0;

        //When
        List<DishDTO> dishDTOList = dishService.listDishes();

        //Then
        assertEquals(emptyListOfDishes,dishDTOList.size());
    }
}
