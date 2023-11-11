package com.pl.service;

import com.pl.auth.Role;
import com.pl.exception.NotFoundException;
import com.pl.model.*;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository  orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    private Address address;
    private User user1;
    private Dish dish1;
    private Restaurant restaurant;
    private OrderCreateDTO orderCreateDTO;
    private Order orderNoDto;
    private Order orderNoDto2;
    private OrderDTO order1DTO;
    private OrderDTO order2DTO;

    private OrderCreateDTO orderCreateWithWrongUserId;
    private OrderCreateDTO orderCreateWithWrongRestaurantrId;
    private OrderCreateDTO orderCreateWithWrongAddressrId;
    private List<Long> dishListLong;
    private List<Dish> dishList;
    private List<Order>orders;


    @BeforeEach
    void dataForTest(){
        user1 = new User("firstName","lastName","email@email.com","password", Role.USER);
        userRepository.save(user1);
        address = new Address("address","address","address","address");
        addressRepository.save(address);
        restaurant = new Restaurant("name");
        restaurantRepository.save(restaurant);
        dish1 = new Dish("dish","desc",new BigDecimal(10),restaurant);
        dishListLong = List.of(dish1.getId());
        dishList = List.of(dish1);
        dishRepository.saveAll(dishList);
        orderCreateDTO = new OrderCreateDTO(user1.getId(), List.of(dish1.getId()),address.getId(),restaurant.getId());
        order1DTO = new OrderDTO(LocalDateTime.now(),new BigDecimal(60),user1.getId(), dishListLong,address.getId(),restaurant.getId());
        order2DTO = new OrderDTO(LocalDateTime.now(),new BigDecimal(60),user1.getId(), dishListLong,address.getId(),restaurant.getId());
        orderNoDto = new Order(LocalDateTime.now(),new BigDecimal(60),"status",user1, dishList,address,restaurant);
        orderNoDto2 = new Order(LocalDateTime.now(),new BigDecimal(60),"status",user1, dishList,address,restaurant);
        orderRepository.save(orderNoDto);
        orderRepository.save(orderNoDto2);

        orderCreateWithWrongUserId = new OrderCreateDTO(12L, dishListLong,address.getId(),restaurant.getId());
        orderCreateWithWrongRestaurantrId = new OrderCreateDTO(user1.getId(), dishListLong,address.getId(),12020L);
        orderCreateWithWrongAddressrId = new OrderCreateDTO(user1.getId(), dishListLong,1L,restaurant.getId());
    }


    @BeforeTestExecution
    void cleanUpBefore(){
        userRepository.deleteAll();
        addressRepository.deleteAll();
        orderRepository.deleteAll();
    }



    @AfterEach
     void cleanUpBeforeEach(){
        userRepository.deleteAll();
        addressRepository.deleteAll();
        orderRepository.deleteAll();
    }



    @Test
    void shouldCreateAndSaveOrder(){
        //Given

        //When
        OrderDTO savingOrder = orderService.create(orderCreateDTO);

        //Then
        assertEquals(user1.getId(),savingOrder.userId());
    }

    @Test
    void shouldCalculateTotalPriceCorrectly(){
        //Given
        //When
        OrderDTO savingOrder = orderService.create(orderCreateDTO);

        //Then
        assertEquals(new BigDecimal("10.00"),savingOrder.totalPrice());


    }



    @Test
    void shouldHandleNotFoundExceptionWhenUserIsWrong() {
        // Given

        // When
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongUserId));

        // Then
        assertNotNull(exception);

    }


    @Test
    void shouldHandleNotFoundExceptionWhenRestaurantIsWrong(){
        //Given

        //Whne
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongRestaurantrId));

        //Thne
       assertNotNull(notFoundException);
    }

    @Test
    void shouldHandleNotFoundExceptionWhenAddressIsWrong(){
        //Given

        //Whne
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongAddressrId));

        //Thne
        assertNotNull(notFoundException);
    }

    @Test
    void shouldFindListOfAllOrders(){
        //Given
        //When
        List<OrderDTO> orderList = orderService.list();

        //Then
        assertEquals(2,orderList.size());
    }

    @Test
    void shouldFindOrderById(){
        //Given
        Order save = orderRepository.save(orderNoDto);
        long orderId = save.getId();

        //When
        OrderDTO getById = orderService.getOrderById(orderId);

        //Then
        assertEquals(user1.getId(),getById.userId());
        assertEquals(address.getId(),getById.deliveryAddressId());
    }

    @Test
    void shouldRemoveOrder() {
        // Given
        Order orderToBeDeleted = orderRepository.save(orderNoDto);
        long orderId = orderToBeDeleted.getId();


        // When
        orderService.remove(orderId);

        // Then
        assertFalse(orderRepository.existsById(orderId));

    }







}
