package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.model.Category;
import com.pl.model.Dish;
import com.pl.model.Restaurant;
import com.pl.model.dto.DishDTO;
import com.pl.repository.DishRepository;
import com.pl.repository.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishService dishService;


    private Dish dish1;
    private Dish dish2;
    private Dish dish3;
    private Restaurant restaurant;
    private DishDTO dishDTO;
    private DishDTO dishDTOWithNull;
    private DishDTO dishDTOSave;
    private DishDTO modifiedDish;
    private List<Dish> dishList;


    @BeforeEach
    void dataForTests() {
        restaurant = new Restaurant();
        restaurantRepository.save(restaurant);
        dish1 = new Dish("dish1", "descriotion1",new BigDecimal(30),restaurant);
        dish2 = new Dish("dish2", "descriotion2",new BigDecimal(30),restaurant);
        dish3 = new Dish("dish3", "descriotion3",new BigDecimal(30),restaurant);
        dishDTO = new DishDTO("dish_DTO", "description_DTO",new BigDecimal(30),restaurant.getId(), Category.APPETIZER);
        dishDTOWithNull = new DishDTO("dish_DTO", "description_DTO",new BigDecimal(30),3L,Category.APPETIZER);
        modifiedDish = new DishDTO("dish_DTO_modified", "description_DTO_modified",new BigDecimal(30),1L,Category.APPETIZER);
        dishList = List.of(dish1, dish2, dish3);
    }

    @BeforeEach
    void cleanUpBefore() {
        dishRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @AfterEach
    void cleanUpAfter() {
        dishRepository.deleteAll();
        restaurantRepository.deleteAll();
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
    void shouldHandleExceptionWhenDishIdIsWrong() {
        //Given
        long wrongDishID = 1231;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> dishService.getDishById(wrongDishID));

        //Thne
        String expectedMessage = "Not found with given id " + wrongDishID;
        assertEquals(expectedMessage, notFoundException.getMessage());
    }

    @Test
    void shouldReturnEmptyListWhenDishListAreEmpty() {
        //Give
        int emptyListOfDishes =0;

        //When
        List<DishDTO> dishDTOList = dishService.listDishes();

        //Then
        assertEquals(emptyListOfDishes,dishDTOList.size());
    }

    @Test
    void shouldSaveDishToDb(){
        //Given
        Restaurant save = restaurantRepository.save(restaurant);
        dishDTOSave = new DishDTO("dish_DTO",
                "description_DTO",new BigDecimal(30),
                save.getId(),Category.APPETIZER);

        //When
        DishDTO savedDish = dishService.createDish(dishDTOSave);

        //Thne
        List<Dish> all = dishRepository.findAll();
        assertEquals(1,all.size());
        assertEquals("dish_DTO",savedDish.name());
    }

    @Test
    void shouldHandleExceptionWhenRestaurantIdIsNullDuringSave(){
        //Given

        //Whne
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> dishService.createDish(dishDTOWithNull));

        assertEquals("Restaurant Not Found", notFoundException.getMessage());
    }

    @Test
    void shouldRemoveDishFromDb(){
        //Given
        dishRepository.saveAll(dishList);

        //When
        List<Dish> dishListBeforeDelete = dishRepository.findAll();
        dishService.removeDish(dish1.getId());
        List<Dish> dishListAfterDelete = dishRepository.findAll();

        //Thne
        assertEquals(3,dishListBeforeDelete.size());
        assertEquals(2,dishListAfterDelete.size());
    }

    @Test
    void shouldHandleNotFoundExceptionWhenIdIsWrongWhenTryToDelete(){
        //Given
        long wrongId = 9999;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> dishService.removeDish(wrongId));
        //Thne
        String expectedMessage = "Not found with given id " + wrongId; //<-- need to change this message
        assertTrue(notFoundException.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldModifyDish(){
        //Given
        Dish savedDish = dishRepository.save(dish1);

        //When
        Optional<Dish> dishBeforeModify = dishRepository.findById(savedDish.getId());
        dishService.editDish(savedDish.getId(), modifiedDish);
        Optional<Dish> dishAfterModify = dishRepository.findById(savedDish.getId());

        //Then
        String expectedNameBeforeUpdate ="dish1";
        String expectedNameAfterUpdate = "dish_DTO_modified";
        assertEquals(expectedNameBeforeUpdate,dishBeforeModify.get().getName());
        assertEquals(expectedNameAfterUpdate,dishAfterModify.get().getName());
    }

    @Test
    void shouldHandleDishNotFoundIfIdIsWrongWhenTryToModify(){
        //Given
        long wrongId = 23323;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> dishService.editDish(wrongId, modifiedDish));

        //Then
        String expectedNessage = "Dish Not found";
        assertEquals(expectedNessage,notFoundException.getMessage());

    }





}
