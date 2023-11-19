package com.pl.repository;

import com.pl.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndHouseNumber(String street, String houseNumber);
}
