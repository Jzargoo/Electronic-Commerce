package com.jzargo.mapper;

import com.jzargo.shared.model.AddressDto;
import com.jzargo.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateMapper implements Mapper<AddressDto, Address> {

    @Override
    public Address map(AddressDto addressCreateDTO) {
        Address address = new Address();
        address.setStreet(addressCreateDTO.getStreet());
        address.setCity(addressCreateDTO.getCity());
        address.setCountry(addressCreateDTO.getCountry());
        address.setZipCode(addressCreateDTO.getZipCode());
        return address;
    }

}
