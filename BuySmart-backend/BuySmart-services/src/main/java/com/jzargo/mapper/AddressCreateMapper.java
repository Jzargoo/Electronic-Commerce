package com.jzargo.mapper;

import com.jzargo.entity.Address;
import com.jzargo.repository.UserSettingsRepository;
import com.jzargo.shared.model.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateMapper implements Mapper<AddressDto, Address> {

    private final UserSettingsRepository userSettingsRepository;

    public AddressCreateMapper(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public Address map(AddressDto addressCreateDTO) {
        Address address = new Address();
        address.setStreet(addressCreateDTO.getStreet());
        address.setCity(addressCreateDTO.getCity());
        address.addUserSettings(
                userSettingsRepository.findByUserId(addressCreateDTO
                        .getUserId())
                        .orElseThrow()
        );
        address.setCountry(addressCreateDTO.getCountry());
        address.setZipCode(addressCreateDTO.getZipCode());
        return address;
    }

}
