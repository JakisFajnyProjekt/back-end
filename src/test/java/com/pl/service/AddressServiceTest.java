package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.model.Address;
import com.pl.model.dto.AddressCreateDTO;
import com.pl.model.dto.AddressDTO;
import com.pl.repository.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    private Address address;
    private Address address2;
    private Address address3;
    private AddressCreateDTO addressDTO;
    private AddressCreateDTO addressDTOWithNull;


    @BeforeEach
    void dataForTests() {
        address = new Address("15", "street", "city", "postalCode");
        address2 = new Address("16", "street", "city", "postalCode");
        address3 = new Address("16", "street", "city", "postalCode");
        addressDTO = new AddressCreateDTO("15_dto", "street_dto", "city_dto", "postalCode_dto");
        addressDTOWithNull = new AddressCreateDTO( null,"street_dto", "city_dto", "postalCode_dto");

    }

    @AfterEach
    void cleanUp() {
        addressRepository.deleteAll();
    }

    @BeforeTestExecution
    void cleanUpBefore() {
        addressRepository.deleteAll();
    }

    @BeforeEach
    void mock() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldReturnEmptyListIfIsNoAddressesInDb() {
        //Given

        //When
        List<AddressDTO> listOfAddresses = addressService.addressesList();

        //Then
        assertEquals(0, listOfAddresses.size());

    }


    @Test
    void shouldCreateAndSaveAddress() {
        // Given


        // When
        AddressDTO address1 = addressService.createAddress(addressDTO);

        // Then

        assertEquals(1, addressRepository.findAll().size());
        assertEquals("15_dto", address1.houseNumber());
    }

    @Test
    void shouldHandleExceptionIfTryTOSaveWithNull() {
        //Given

        //When && Then
        assertThrows(NullPointerException.class,
                () -> addressService.createAddress(addressDTOWithNull));
    }

    @Test
    void shouldFindAddressById() {
        // Given
        Address savedAddress = addressRepository.save(address);
        long addressId = savedAddress.getId();

        // When
        AddressDTO result = addressService.getAddressById(addressId);

        // Then
        assertNotNull(result);
        assertEquals("15", result.houseNumber());
        assertEquals("street", result.street());
        assertEquals("city", result.city());
    }

    @Test
    void shouldHandleWrongIdWhileRetrieveById() {
        //Given
        long wrongId = 123L;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> addressService.getAddressById(wrongId));

        //Then
        String expectedMessage = "Not found with given id " + wrongId;
        assertTrue(notFoundException.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldFindListOfAddressesIfExist() {
        //Given
        List<Address> addresses = List.of(address, address2, address3);
        addressRepository.saveAll(addresses);

        //When
        List<AddressDTO> addressDTOS = addressService.addressesList();

        //Then
        assertEquals(3, addressDTOS.size());
    }

    @Test
    void shouldDeleteAddressFrmDb() {
        //Given
        Address savedAddress = addressRepository.save(address);
        long addressId = savedAddress.getId();

        //When
        List<Address> beforeDelete = addressRepository.findAll();
        addressService.deleteAddress(addressId);
        List<Address> afterDelete = addressRepository.findAll();

        //Then
        assertEquals(1, beforeDelete.size());
        assertEquals(0, afterDelete.size());


    }


}
