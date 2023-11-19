package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.model.Address;
import com.pl.model.Restaurant;
import com.pl.model.dto.RestaurantDTO;
import com.pl.repository.RestaurantRepository;
import com.pl.service.RestaurantService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private RestaurantRepository restaurantRepository;


    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;
    private RestaurantDTO restaurantDTO1;
    private RestaurantDTO restaurantDTO2;
    private Address address;

    @BeforeEach
    void dataForTests() {
        address = new Address("15", "street", "city", "postalCode");
        restaurant = new Restaurant("restaurant", address);
        restaurantDTO = new RestaurantDTO("restaurant_test_name1", address.getId());
        restaurantDTO1 = new RestaurantDTO("restaurant_test_name2", address.getId());
        restaurantDTO2 = new RestaurantDTO("restaurant_test_name3", address.getId());
    }

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @AfterEach
    void cleanUpAfter() {
        restaurantRepository.deleteAll();
    }

    @Test
    void shouldSaveRestaurant() throws Exception {
        //Given
        when(restaurantService.create(restaurantDTO)).thenReturn(restaurantDTO);

        //When
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(restaurantDTO.name()))
                .andExpect(jsonPath("$.restaurantAddress").value(address.getId()));
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        //Given
        long restaurantId = 1231231L;
        when(restaurantService.findById(restaurantId)).thenReturn(restaurantDTO);

        //When
        mockMvc.perform(get("/api/restaurants/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("restaurant_test_name1"))
                .andExpect(jsonPath("$.restaurantAddress").value(address.getId()));
    }

    @Test
    void shouldFindListOfRestaurants() throws Exception {
        //Given
        List<RestaurantDTO> listOfRestaurants = List.of(restaurantDTO, restaurantDTO1, restaurantDTO2);
        when(restaurantService.list()).thenReturn(listOfRestaurants);
        //When
        mockMvc.perform(get("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(listOfRestaurants.size()))
                .andExpect(jsonPath("$.[0].name").value("restaurant_test_name1"))
                .andExpect(jsonPath("$.[1].name").value("restaurant_test_name2"))
                .andExpect(jsonPath("$.[2].name").value("restaurant_test_name3"));

    }

}
