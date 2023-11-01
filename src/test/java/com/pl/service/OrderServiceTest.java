package com.pl.service;

import com.pl.auth.Role;
import com.pl.exception.NotFoundException;
import com.pl.model.*;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
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
    private OrderDTO order1;
    private OrderDTO order2;

    private OrderCreateDTO orderCreateWithWrongUserId;
    private OrderCreateDTO orderCreateWithWrongRestaurantrId;
    private OrderCreateDTO orderCreateWithWrongAddressrId;
    private List<Long> dishListLong;
    private List<Dish> dishList;
    private List<OrderDTO>orders;


    @BeforeEach
    void dataForTest(){
        MockitoAnnotations.openMocks(this);

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
        orderCreateDTO = new OrderCreateDTO(user1.getId(), dishListLong,address.getId(),restaurant.getId());
        order1 = new OrderDTO(LocalDateTime.now(),new BigDecimal(60),user1.getId(), dishListLong,address.getId(),restaurant.getId());
        order2 = new OrderDTO(LocalDateTime.now(),new BigDecimal(60),user1.getId(), dishListLong,address.getId(),restaurant.getId());
        orderNoDto = new Order(LocalDateTime.now(),new BigDecimal(60),"status",user1, dishList,address,restaurant);

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
        when(orderService.create(orderCreateDTO)).thenReturn(order1);

        //When
        OrderDTO savingOrder = orderService.create(orderCreateDTO);

        //Then
        assertEquals(user1.getId(),savingOrder.userId());
    }

    @Test
    void shouldCalculateTotalPriceCorrectly(){
        //Given
        when(orderService.create(orderCreateDTO)).thenReturn(order1);

        //When
        OrderDTO savingOrder = orderService.create(orderCreateDTO);

        //Then
        assertEquals(new BigDecimal(60),savingOrder.totalPrice());


    }



    @Test
    void shouldHandleNotFoundExceptionWhenUserIsWrong() {
        // Given
        when(orderService.create(orderCreateWithWrongUserId)).thenThrow(NotFoundException.class);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongUserId));

        // Then
        assertNotNull(exception);

    }


    @Test
    void shouldHandleNotFoundExceptionWhenRestaurantIsWrong(){
        //Given
        when(orderService.create(orderCreateWithWrongRestaurantrId)).thenThrow(NotFoundException.class);

        //Whne
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongRestaurantrId));

        //Thne
       assertNotNull(notFoundException);
    }

    @Test
    void shouldHandleNotFoundExceptionWhenAddressIsWrong(){
        //Given
        when(orderService.create(orderCreateWithWrongAddressrId)).thenThrow(NotFoundException.class);

        //Whne
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> orderService.create(orderCreateWithWrongAddressrId));

        //Thne
        assertNotNull(notFoundException);
    }

    @Test
    void shouldFindListOfAllOrders(){
        //Given
        orders = List.of(order1,order2);
        when(orderService.list()).thenReturn(orders);

        //When
        List<OrderDTO> orderList = orderService.list();

        //Then
        assertEquals(2,orderList.size());
        assertEquals(order1.orderTime(),orderList.get(0).orderTime());
    }

    @Test
    void shouldFindOrderById(){
        //Given
        long orderId = 1L;
        when(orderService.create(orderCreateDTO)).thenReturn(order1);
        when(orderService.getOrderById(orderId)).thenReturn(order1);

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
        assertTrue(orderRepository.existsById(orderId));

    }







}
