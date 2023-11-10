package com.pl.mapper;

import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    private Address address;
    private Address address1;
    private Address address2;
    private AddressDTO addressDTO;
    private AddressDTO addressDTO1;
    private AddressDTO addressDTO2;

    @BeforeEach
    void dataForTests(){
        address = new Address("15","street","city","postalCode");
        address1 = new Address("151","street1","city1","postalCode1");
        address2 = new Address("152","street2","city2","postalCode2");
        addressDTO = new AddressDTO("15_dto","street_dto","city_dto","postalCode_dto");
        addressDTO1 = new AddressDTO("151_dto","street1_dto","city1_dto","postalCode1_dto");
        addressDTO2 = new AddressDTO("152_dto","street2_dto","city2+dto","postalCode2_dto");
    }

    @Test
    void shouldMapToAddressDTO(){
        //Given
        when(addressMapper.mapToDTO(address)).thenReturn(addressDTO);

        //When
        AddressDTO toDto = addressMapper.mapToDTO(address);

        //Then
        assertEquals(AddressDTO.class, toDto.getClass());
        assertEquals("15_dto", toDto.houseNumber());
        assertEquals("street_dto", toDto.street());
        assertEquals("city_dto", toDto.city());
    }

    @Test
    void shouldMapToAddressFromDTO(){
        //Given
        when(addressMapper.mapFromDTO(addressDTO)).thenReturn(address);

        //When
        Address fromDTO = addressMapper.mapFromDTO(addressDTO);

        //Then
        assertEquals(address.getClass(),fromDTO.getClass());
        assertEquals("15", fromDTO.getHouseNumber());
        assertEquals("street", fromDTO.getStreet());
        assertEquals("city", fromDTO.getCity());
    }

    @Test
    void shouldMapListDTO(){
        //Given
        List<Address> addressesList = List.of(address,address1,address2);
        List<AddressDTO> expectedList = List.of(addressDTO, addressDTO1, addressDTO2);
        when(addressMapper.mapToList(addressesList)).thenReturn(expectedList);

        //When
        List<AddressDTO> addressesListDTO = addressMapper.mapToList(addressesList);

        //Then
        assertEquals(3,addressesListDTO.size());
    }







}
