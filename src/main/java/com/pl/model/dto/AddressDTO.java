package com.pl.model.dto;

public record AddressDTO(
        Long addressId,
        String houseNumber,
        String street,
        String city,
        String postalCode) {
}
