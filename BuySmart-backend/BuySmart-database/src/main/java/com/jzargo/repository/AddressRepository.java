package com.jzargo.repository;

import com.jzargo.entity.Address;
import com.jzargo.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address>findAddressesByUserSettings(UserSettings userSettings);
}
