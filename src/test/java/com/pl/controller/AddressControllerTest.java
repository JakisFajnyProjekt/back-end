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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AddressService addressService;

    @Autowired
    private AddressController addressController;

    private AddressDTO addressDTO;
    private AddressDTO addressDTO1;
    private AddressDTO addressDTO2;


    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @BeforeEach
    void dataForTests() {
        addressDTO = new AddressDTO(1L,"15", "street", "city", "postalCode");
        addressDTO1 = new AddressDTO(2L,"151", "street1", "city1", "postalCode1");
        addressDTO2 = new AddressDTO(3L,"152", "street2", "city2", "postalCode2");
    }

    @Test
    void shouldCreateAddress() throws Exception {
        //Given
        when(addressService.createAddress(addressDTO)).thenReturn(addressDTO);

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDTO)))
                .andExpect(jsonPath("$.houseNumber").value("15"))
                .andExpect(jsonPath("$.street").value("street"))
                .andExpect(jsonPath("$.city").value("city"))
                .andExpect(jsonPath("$.postalCode").value("postalCode"));
    }

    @Test
    void shouldRetrieveAddressById() throws Exception {
        //Given
        long addresId = 12L;
        when(addressService.getAddressById(addresId)).thenReturn(addressDTO);

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/{addressId}", addresId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.houseNumber").value("15"))
                .andExpect(jsonPath("$.street").value("street"))
                .andExpect(jsonPath("$.city").value("city"))
                .andExpect(jsonPath("$.postalCode").value("postalCode"));
    }

    @Test
    void shouldRetrieveListOfAddresses() throws Exception {
        //Given
        when(addressService.addressesList()).thenReturn(List.of(addressDTO, addressDTO1, addressDTO2));

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void shouldRemoveAddress() throws Exception {
        //Given
        long addressId = 12L;

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/addresses/{addressId}", addressId))
                .andExpect(status().isAccepted());

        //Then
        verify(addressService).deleteAddress(addressId);
        AddressDTO addressById = addressService.getAddressById(addressId);
        assertNull(addressById);


    }


}
