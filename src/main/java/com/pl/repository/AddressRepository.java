package com.pl.repository;

import com.pl.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Optionals;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

     Optional<Address> findByHouseNumber(String houseNumber);
     Optional<Address> findByStreet(String street);
}
