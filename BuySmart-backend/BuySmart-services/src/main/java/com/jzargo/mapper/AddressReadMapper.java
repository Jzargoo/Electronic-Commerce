package com.jzargo.mapper;

import com.jzargo.shared.model.AddressDto;
import com.jzargo.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressReadMapper implements Mapper<Address, AddressDto> {

    @Override
    public AddressDto map(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }
}
