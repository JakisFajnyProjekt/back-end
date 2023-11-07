package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.model.dto.AddressDTO;
import com.pl.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class AddressControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressController addressController;

    @MockBean
    private AddressService addressService;

    private AddressDTO addressDTO;
    private AddressDTO addressDTO1;
    private AddressDTO addressDTO2;




    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @BeforeEach
    void dataForTests(){
        addressDTO = new AddressDTO("15","street","city","postalCode");
        addressDTO1 = new AddressDTO("151","street1","city1","postalCode1");
        addressDTO2 = new AddressDTO("152","street2","city2","postalCode2");
    }

    @Test
    void shouldCreateAddress() throws Exception {
        //Given
        when(addressService.createAddress(addressDTO)).thenReturn(addressDTO);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(jsonPath("$.city").value("city"));

        //Thne
    }


}
