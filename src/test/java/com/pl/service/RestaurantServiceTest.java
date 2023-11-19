package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.model.Address;
import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.AddressRepository;
import com.pl.repository.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;

    private Restaurant restaurant;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;
    private RestaurantDTO restaurantDTO;
    private Address address;
    private Address address2;
    private Address address3;
    private RestaurantDTO restaurantDTOWithWrongId;

    @BeforeEach
    void dataForTests() {
        address = new Address("123", "street", "city", "postalCode");
        address2 = new Address("123", "street", "city", "postalCode");
        address3 = new Address("123", "street", "city", "postalCode");
        restaurant = new Restaurant("restaurant");
        restaurant1 = new Restaurant("restaurant", address);
        restaurant2 = new Restaurant("restaurant", address2);
        restaurant3 = new Restaurant("restaurant", address3);

    }

    @BeforeTestExecution
    public void beforeAllTests() {
        restaurantRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @AfterEach
    void afterEachTest() {
        addressRepository.deleteAll();
        restaurantRepository.deleteAll();
    }


    @Test
    void shouldCreateAndSaveRestaurant() {
        //Given
        Address savedAddress = addressRepository.save(address);
        restaurantDTO = new RestaurantDTO("restaurant_dto", savedAddress.getId());

        //When
        restaurantService.create(restaurantDTO);

        //Thne
        assertEquals(1, restaurantRepository.findAll().size());
    }

    @Test
    void shouldHandleWrongAddressWhenCreatingRestaurant() {
        //Given
        long wrongIdAddress = 1L;
        restaurantDTOWithWrongId = new RestaurantDTO("name", wrongIdAddress);

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> restaurantService.create(restaurantDTO));

        //Thne
        assertNotNull(notFoundException);
        assertTrue(notFoundException.getMessage().contains("Address not found"));

    }


    @Test
    void shouldFindRestaurantById() {
        //Given
        Address savedAddress = addressRepository.save(address);
        restaurant.setAddress(savedAddress);
        Restaurant savedRestaurants = restaurantRepository.save(restaurant);


        //When
        RestaurantDTO findRestaurant = restaurantService.findById(savedRestaurants.getId());

        //Then
        assertEquals("restaurant", findRestaurant.name());
        assertNotNull(restaurantRepository.findById(findRestaurant.restaurantAddress()));
    }

    @Test
    void shouldFindAllRestaurants() {
        //Given
        addressRepository.save(address);
        addressRepository.save(address2);
        addressRepository.save(address3);
        List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);
        restaurantRepository.saveAll(restaurants);


        //When
        List<RestaurantDTO> list = restaurantService.list();

        //Then
        assertEquals(3, list.size());
    }


}
