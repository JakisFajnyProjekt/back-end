package com.pl.controller;

import com.pl.model.dto.AddressCreateDTO;
import com.pl.model.dto.AddressDTO;
import com.pl.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddressDTO addAddress(@RequestBody @Valid AddressCreateDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @GetMapping("/all")
    public List<AddressDTO> list() {
        return addressService.addressesList();
    }

    @GetMapping("{addressId}")
    public AddressDTO findById(@PathVariable long addressId) {
        return addressService.getAddressById(addressId);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
