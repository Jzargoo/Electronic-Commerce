package com.jzargo.services;

import com.jzargo.shared.model.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(Long id);

    AddressDto addAddress(AddressDto addressDto);
}