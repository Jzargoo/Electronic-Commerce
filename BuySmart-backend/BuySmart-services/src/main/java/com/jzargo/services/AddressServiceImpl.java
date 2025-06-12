package com.jzargo.services;

import com.jzargo.entity.Address;
import com.jzargo.entity.UserSettings;
import com.jzargo.mapper.AddressCreateMapper;
import com.jzargo.mapper.AddressReadMapper;
import com.jzargo.repository.AddressRepository;
import com.jzargo.repository.UserSettingsRepository;
import com.jzargo.shared.model.AddressDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    private final AddressReadMapper addressReadMapper;
    private final AddressCreateMapper addressCreateMapper;
    private final UserSettingsRepository userSettingsRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressReadMapper addressReadMapper, AddressCreateMapper addressCreateMapper, UserSettingsRepository userSettingsRepository) {
        this.addressRepository = addressRepository;
        this.addressReadMapper = addressReadMapper;
        this.addressCreateMapper = addressCreateMapper;
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public List<AddressDto> getAddresses(Long id) {
        UserSettings userSettings = userSettingsRepository.findByUserId(id).orElseThrow();
        return addressRepository.findAddressesByUserSettings(
                userSettings).stream()
                .map(addressReadMapper::map)
                .toList();
    }

    @Override
    @Transactional
    public AddressDto addAddress(AddressDto addressDto) {
        Address map = addressCreateMapper.map(addressDto);
        return Optional.of(
                addressRepository.saveAndFlush(map))
                .map(addressReadMapper::map).orElseThrow();
    }
}
