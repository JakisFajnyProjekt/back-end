package com.pl.service;

import com.pl.exception.AddressAlreadyExist;
import com.pl.model.Address;
import com.pl.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceValidation {
    private final AddressRepository addressRepository;
    private static  final Logger LOGGER = LoggerFactory.getLogger(AddressServiceValidation.class);

    public AddressServiceValidation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void validateAddress(String houseNumber, String street) {
        Optional<Address> byStreetAndHouseNumber = addressRepository
                .findByStreetAndHouseNumber(street, houseNumber);
        if (byStreetAndHouseNumber.isPresent()){
            LOGGER.error("address exist in DB");
            throw new AddressAlreadyExist("address already exist");
        }

    }


}
