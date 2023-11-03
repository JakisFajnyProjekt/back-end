package com.pl.service;

import com.pl.mapper.AddressMapper;
import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import com.pl.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressService extends AbstractService<AddressRepository, Address> {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDTO getAddressById(Long addresId) {
        Address findAddress = findEntity(addressRepository, addresId);
        LOGGER.info("addres finded with id " + findAddress.getId());
        return addressMapper.mapToDTO(findAddress);
    }

    public List<AddressDTO> list() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            LOGGER.info("no addresses found");
            return new ArrayList<>();
        }
        LOGGER.info("list of " + addresses.size() + " founded");
        return addressMapper.mapToList(addresses);
    }

    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) {
        try {
            Address validatedAddressObj = addressCheck(addressDTO);
            Address savedAddress = addressRepository.save(validatedAddressObj);
            LOGGER.info("address saved");
            return addressMapper.mapToDTO(savedAddress);
        } catch (Exception n) {
            LOGGER.error(n.getMessage());
            throw new IllegalArgumentException("house number, city, street, and postal code cannot be empty");
        }
    }

    private Address addressCheck(AddressDTO addressDTO) {
        Objects
                .requireNonNull(addressDTO.houseNumber(), "houseNumber cannot be null");
        Objects
                .requireNonNull(addressDTO.city(), "city cannot be null");
        Objects
                .requireNonNull(addressDTO.postalCode(), "postal code cannot be null");
        Objects
                .requireNonNull(addressDTO.street(), "street cannot be null");
        LOGGER.info("address checked");
        return addressMapper.mapFromDTO(addressDTO);

    }


}
