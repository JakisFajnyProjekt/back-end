package com.pl.mapper;

import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public AddressDTO mapToDTO(Address address){
        return new AddressDTO(address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode());
    }


    public Address mapFromDTO(AddressDTO addressDTO){
        return new Address(addressDTO.houseNumber(),
                addressDTO.street(),
                addressDTO.city(),
                addressDTO.postalCode());
    }


    public List<AddressDTO>mapToList(List<Address> address){
        return  address.stream()
                .map(this::mapToDTO)
                .toList();
    }


}
