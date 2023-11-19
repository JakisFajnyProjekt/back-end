package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.model.Category;
import com.pl.model.Dish;
import com.pl.model.dto.DishDTO;
import com.pl.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class DishControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DishService dishService;
    @Autowired
    private DishController dishController;
    private Dish dish1;
    private DishDTO dishDTO1;
    private DishDTO dishDTO2;
    private DishDTO dishDTOForUpdateName;
    private List<DishDTO> dishList;


    @BeforeEach
    void testData() {
        dish1 = new Dish("dishName1", "description1");
        dishDTO1 = new DishDTO("nameDto", "descriptionDTO", new BigDecimal(30), 1L, Category.APPETIZER);
        dishDTO2 = new DishDTO("nameDto2", "descriptionDTO2", new BigDecimal(12), 1L, Category.APPETIZER);
        dishDTOForUpdateName = new DishDTO("nameUpdate", "descriptionDTO", new BigDecimal(30), 1L, Category.APPETIZER);
        dishList = List.of(dishDTO1, dishDTO2);

    }

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldCreateDish() throws Exception {
        // Given
        when(dishService.createDish(any(DishDTO.class))).thenReturn(dishDTO1);

        // When && Then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/dishes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("nameDto"))
                .andExpect(jsonPath("$.description").value("descriptionDTO"))
                .andExpect(jsonPath("$.price").value(30))
                .andExpect(jsonPath("$.restaurantId").value(1L));
    }

    @Test
    void shouldFindDishById() throws Exception {
        // Given
        long dishId = 1L;

        // When
        when(dishService.getDishById(dishId)).thenReturn(dishDTO1);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dishes/{dishId}", dishId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("nameDto"))
                .andExpect(jsonPath("$.description").value("descriptionDTO"))
                .andExpect(jsonPath("$.price").value(30))
                .andExpect(jsonPath("$.restaurantId").value(1L));
    }

    @Test
    void shouldFindListOfAllDishes() throws Exception {
        //Given
        when(dishService.listDishes()).thenReturn(dishList);


        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dishes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].name").value("nameDto"))
                .andExpect(jsonPath("$.[1].name").value("nameDto2"));

    }

    @Test
    void shouldDeleteDishWithGivenId() throws Exception {
        // Given
        long dishId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/dishes/" + dishId))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        verify(dishService).removeDish(dishId);

        DishDTO dish = dishService.getDishById(dishId);
        assertNull(dish);
    }

    @Test
    void shouldModifyDish() throws Exception {
        //Given
        long dishId = 12L;
        when(dishService.createDish(dishDTO1)).thenReturn(dishDTO1);
        doNothing().when(dishService).editDish(dishId, dishDTOForUpdateName);


        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/dishes/" + dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishDTOForUpdateName)))
                .andExpect(status().isAccepted());
    }


}
