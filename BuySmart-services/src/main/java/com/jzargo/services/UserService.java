package com.jzargo.services;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserReadDto> findById(Long id);
    List<UserReadDto> findAll();
    UserReadDto create(UserCreateAndUpdateDto dto);
    UserReadDto update(Long id, UserCreateAndUpdateDto dto);
    boolean delete(Long id);
}