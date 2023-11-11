package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.auth.Role;
import com.pl.model.*;
import com.pl.model.dto.OrderCreateDTO;
import com.pl.model.dto.OrderDTO;
import com.pl.repository.*;
import com.pl.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private Address address;
    private Restaurant restaurant;
    private User user;
    private OrderCreateDTO orderCreateDTO;
    private Dish dish1;
    private Dish dish3;
    private Dish dish2;
    private OrderDTO orderDTO;
    private OrderDTO orderDTO1;
    private OrderDTO orderDTO2;
    private Order order;


    @BeforeEach
    void dataForTests(){
        address = new Address("15","street","city","postalCode");
        restaurant = new Restaurant("restaurant",address);
        user = new User("firstname1", "lastname", "password", "email@email.com", Role.USER);
        userRepository.save(user);
        dish1 = new Dish("name", "description", new BigDecimal(30), restaurant, Category.APPETIZER);
        dish2 = new Dish("name", "description", new BigDecimal(30), restaurant, Category.APPETIZER);
        dish3 = new Dish("name", "description", new BigDecimal(30), restaurant, Category.APPETIZER);
        orderCreateDTO = new OrderCreateDTO(user.getId(), List.of(dish1.getId(),dish2.getId()),address.getId(),restaurant.getId());
        orderDTO = new OrderDTO(LocalDateTime.now(),new BigDecimal(90),user.getId(), List.of(dish1.getId(),dish2.getId(),dish3.getId()),address.getId(),restaurant.getId());
        orderDTO1 = new OrderDTO(LocalDateTime.now(),new BigDecimal(90),user.getId(), List.of(dish1.getId(),dish2.getId(),dish3.getId()),address.getId(),restaurant.getId());
        orderDTO2 = new OrderDTO(LocalDateTime.now(),new BigDecimal(90),user.getId(), List.of(dish1.getId(),dish2.getId(),dish3.getId()),address.getId(),restaurant.getId());
    }

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @AfterEach
    void cleanUpAfter(){
        orderRepository.deleteAll();
        userRepository.deleteAll();
        dishRepository.deleteAll();
        restaurantRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void shouldCreateOrder() throws Exception{
        //Given
        when(orderService.create(orderCreateDTO)).thenReturn(orderDTO);

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.totalPrice").value(new BigDecimal(90)));
    }

    @Test
    void shouldFindListOFOrders() throws Exception{
        //Given
        when(orderService.list()).thenReturn(List.of(orderDTO,orderDTO1,orderDTO2));

        //Given
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$.[0].totalPrice").value(90));
    }

    @Test
    void shouldFindOrderByGIvenId() throws Exception{
        //Give
        long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").value(new BigDecimal(90)));

    }

    @Test
    void shouldRemoveOrder()throws  Exception{
        //Given
        long orderId = 123L;

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/{orderId}", orderId))
                .andExpect(status().isOk());

        //Then
        verify(orderService).remove(orderId);
        OrderDTO findOrder = orderService.getOrderById(orderId);
        assertNull(findOrder);


        }



}
