package com.pl.service;

import com.pl.exception.AddressAlreadyExist;
import com.pl.mapper.AddressMapper;
import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import com.pl.repository.AddressRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService extends AbstractService<AddressRepository, Address> {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDTO getAddressById(Long addressId) {
        Address findAddress = findEntity(addressRepository, addressId);
        LOGGER.info("address found with id " + findAddress.getId());
        return addressMapper.mapToDTO(findAddress);
    }

    @Cacheable(cacheNames = "findList")
    public List<AddressDTO> addressesList() {
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
            checkIfAddressAlreadyExist(addressDTO.houseNumber(), addressDTO.street());
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

    private void checkIfAddressAlreadyExist(String houseNumber, String street) {
        Optional<Address> byHouseNumber = addressRepository.findByHouseNumber(houseNumber);
        Optional<Address> byStreet = addressRepository.findByStreet(street);
        if (byHouseNumber.isPresent() && byStreet.isPresent()) {
            LOGGER.info("address exist");
            throw new AddressAlreadyExist("Address already exist");
        }
    }

    @Transactional
    public void deleteAddress(long addressId) {
        Address address = findEntity(addressRepository, addressId);
        addressRepository.delete(address);
        LOGGER.info("address with id" + addressId + "deleted");
    }


}
