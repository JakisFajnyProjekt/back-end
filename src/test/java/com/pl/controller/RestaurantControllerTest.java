package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.model.Address;
import com.pl.model.Restaurant;
import com.pl.model.dto.AddressDTO;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.AddressRepository;
import com.pl.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantController restaurantController;


    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;
    private Address address;

    @BeforeEach
    void dataForTests(){
        address = new Address("15","street","city","postalCode");
        restaurant = new Restaurant("restaurant",address);
        restaurantDTO = new RestaurantDTO("restaurant",address.getId());

    }

}
