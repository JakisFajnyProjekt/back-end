package com.pl.controller;

import com.pl.model.dto.AddressDTO;
import com.pl.service.AddressService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @GetMapping("/all")
    public List<AddressDTO> list() {
        return addressService.list();
    }

    @GetMapping("{addressId}")
    public AddressDTO findById(@PathVariable long addressId) {
        return addressService.getAddressById(addressId);
    }
}
