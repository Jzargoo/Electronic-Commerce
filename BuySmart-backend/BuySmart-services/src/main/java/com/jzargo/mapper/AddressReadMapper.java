package com.jzargo.mapper;

import com.jzargo.entity.Address;
import com.jzargo.shared.model.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressReadMapper implements Mapper<Address, AddressDto> {

    @Override
    public AddressDto map(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}
