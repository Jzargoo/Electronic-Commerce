package com.jzargo.services;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.exceptions.DataNotFoundException;

import java.util.Optional;

public interface UserService {
    Optional<UserReadDto> findById(Long id);
    UserReadDto create(UserCreateAndUpdateDto dto);
    UserReadDto update(Long id, UserCreateAndUpdateDto dto);
    boolean delete(Long id);

    UserReadDto findByUsername(String username);

    byte[] getProfileImage(Long id) throws DataNotFoundException;

    boolean updatePassword(String newPassword, String email);
}