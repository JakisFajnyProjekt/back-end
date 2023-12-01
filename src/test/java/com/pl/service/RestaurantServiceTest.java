package com.pl.service;

import com.pl.auth.Role;
import com.pl.exception.NotFoundException;
import com.pl.model.*;
import com.pl.model.dto.OrderByRestaurantDTO;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private UserRepository userRepository;

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
        orderRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void afterEachTest() {
        restaurantRepository.deleteAll();
        orderRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "testuser", password = "testtest", roles = "USER")
    void shouldCreateAndSaveRestaurant() {
        //Given

        Address savedAddress = addressRepository.save(address);
        long addressId = savedAddress.getId();
        restaurantDTO = new RestaurantDTO(1L,"restaurant_dto",addressId);


        //When
        restaurantService.create(restaurantDTO);

        //Then
        assertEquals(1, restaurantRepository.findAll().size());

    }

    @Test
    void shouldHandleWrongAddressWhenCreatingRestaurant() {
        //Given
        long wrongIdAddress = 1L;
        restaurantDTOWithWrongId = new RestaurantDTO(2L,"name", wrongIdAddress);

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

    @Test
    @WithMockUser(username = "testuser@test.com", password = "testtest", roles = "USER")
    void shouldFindOrdersForLoggedRestaurant(){
        //Given
        Address savedAddress = addressRepository.save(address);
        restaurant = new Restaurant("name");
        restaurant.setOwnerEmail("testuser@test.com");
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        Dish dish1 = new Dish("name1", "description1");
        Dish savedDish1 = dishRepository.save(dish1);
        Dish dish2 = new Dish("name2", "description2");
        Dish savedDish2 = dishRepository.save(dish2);
        User user = new User("Jan", "Kowalski", "testuser@test.com", "testtest", Role.USER);
        User savedUser = userRepository.save(user);

        Order order1 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", savedUser, List.of(savedDish1, savedDish2), savedAddress, savedRestaurant
        );
        Order order2 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", savedUser, List.of(savedDish1, savedDish2), savedAddress, savedRestaurant
        );
        Order order3 = new Order(LocalDateTime.now(),
                BigDecimal.valueOf(100), "CREATED", savedUser, List.of(savedDish1, savedDish2), savedAddress, savedRestaurant
        );

        List<Order> orders = List.of(order1, order2, order3);
        orderRepository.saveAll(orders);
        //When
        List<OrderByRestaurantDTO> listForRestaurant = restaurantService.findOrders(savedRestaurant.getId());

        //Then
        assertEquals(3,listForRestaurant.size());

    }


}
