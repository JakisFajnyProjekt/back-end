package com.pl.service;

import com.pl.exception.AddressAlreadyExist;
import com.pl.model.Address;
import com.pl.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceValidation {
    private final AddressRepository addressRepository;

    public AddressServiceValidation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public void validateAddress(String houseNumber, String street) {
        Optional<Address> byHouseNumber = addressRepository.findByHouseNumber(houseNumber);
        Optional<Address> byStreet = addressRepository.findByStreet(street);
        if (byHouseNumber.isPresent() && byStreet.isPresent()) {
            throw new AddressAlreadyExist("Address already exists");
        }
    }


}
