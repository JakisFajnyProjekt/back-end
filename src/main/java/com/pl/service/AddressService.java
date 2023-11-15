package com.pl.service;

import com.pl.mapper.AddressMapper;
import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import com.pl.repository.AddressRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class AddressService extends AbstractService<AddressRepository, Address> {
    private final AddressRepository addressRepository;
    private final AddressServiceValidation addressServiceValidation;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressServiceValidation addressServiceValidation, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressServiceValidation = addressServiceValidation;
        this.addressMapper = addressMapper;
    }
    @Cacheable(value = "addressesList",key = "#addressId")
    public AddressDTO getAddressById(Long addressId) {
        Address findAddress = findEntity(addressRepository, addressId);
        LOGGER.info("address found with id " + findAddress.getId());
        return addressMapper.mapToDTO(findAddress);
    }


    @Cacheable(cacheNames = "addressesList")
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
    @CacheEvict(value = "addressesList", allEntries = true)
    public AddressDTO createAddress(AddressDTO addressDTO) {
            Address validatedAddressObj = addressCheck(addressDTO);
            Address savedAddress = addressRepository.save(validatedAddressObj);
            LOGGER.info("address saved");
            return addressMapper.mapToDTO(savedAddress);

    }


    private Address addressCheck(AddressDTO addressDTO){
        Stream.of(addressDTO.houseNumber(),addressDTO.city(),addressDTO.postalCode(),addressDTO.street())
                .forEach(field->Objects.requireNonNull(field,"value cannot be null"));
        addressServiceValidation.validateAddress(addressDTO.houseNumber(), addressDTO.street());
        LOGGER.info("address checked");
        return addressMapper.mapFromDTO(addressDTO);

    }

    @Transactional
    @CacheEvict(value = "addressesList",allEntries = true)
    public void deleteAddress(long addressId) {
        Address address = findEntity(addressRepository, addressId);
        addressRepository.delete(address);
        LOGGER.info("address with id" + addressId + "deleted");
    }


}
