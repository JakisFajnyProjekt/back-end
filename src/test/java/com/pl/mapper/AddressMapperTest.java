package com.pl.mapper;

import com.pl.model.Address;
import com.pl.model.dto.AddressCreateDTO;
import com.pl.model.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressMapperTest {
@Autowired
    private AddressMapper addressMapper;
    private Address address;
    private Address address1;
    private Address address2;
    private AddressCreateDTO addressDTO;
    private AddressDTO addressDTO1;
    private AddressDTO addressDTO2;

    @BeforeEach
    void dataForTests() {
        address = new Address("15", "street", "city", "postalCode");
        address1 = new Address("151", "street1", "city1", "postalCode1");
        address2 = new Address("152", "street2", "city2", "postalCode2");
        addressDTO = new AddressCreateDTO("15_dto", "street_dto", "city_dto", "postalCode_dto");
        addressDTO1 = new AddressDTO(3L, "151_dto", "street1_dto", "city1_dto", "postalCode1_dto");
        addressDTO2 = new AddressDTO(4L, "152_dto", "street2_dto", "city2+dto", "postalCode2_dto");
    }

    @Test
    void shouldMapToAddressDTO() {
        //Given
        //When
        AddressDTO toDto = addressMapper.mapToDTO(address);
        //Then
        assertNotNull(toDto);
        assertEquals(AddressDTO.class, toDto.getClass());
    }

    @Test
    void shouldMapToAddressFromDTO() {
        //Given
        //When
        Address fromDTO = addressMapper.mapFromDTO(addressDTO);
        //Then
        assertEquals(Address.class, fromDTO.getClass());
    }

    @Test
    void shouldMapListDTO() {
        //Given
        List<Address> addressesList = List.of(address, address1, address2);
        //When
        List<AddressDTO> addressesListDTO = addressMapper.mapToList(addressesList);
        //Then
        assertEquals(3, addressesListDTO.size());
        assertEquals(AddressDTO.class,addressesListDTO.get(0).getClass());
        assertEquals(AddressDTO.class,addressesListDTO.get(1).getClass());
        assertEquals(AddressDTO.class,addressesListDTO.get(2).getClass());
    }
}
