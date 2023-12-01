package com.pl.model.dto;

public record AddressCreateDTO (
        String houseNumber,
        String street,
        String city,
        String postalCode)
{
}
