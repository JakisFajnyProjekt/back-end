package com.pl.service;

import com.pl.mapper.AddressMapper;
import com.pl.model.Address;
import com.pl.model.dto.AddressDTO;
import com.pl.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService extends AbstractService<AddressRepository, Address> {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDTO getAddressById(Long addresId){
        Address findAddress = findEntity(addressRepository, addresId);
        LOGGER.info("addres finded with id "  + findAddress.getId());
        return addressMapper.mapToDTO(findAddress);
    }

    public List<AddressDTO> list(){
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            return new ArrayList<>();
        }
        return addresses.stream()
                .map(addressMapper::mapToDTO)
                .toList();
    }


}
