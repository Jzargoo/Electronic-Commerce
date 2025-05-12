package com.jzargo.services;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.UserAlreadyExistsException;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.jzargo.shared.model.UserReadDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserReadDto> findById(Long id);
    UserReadDto create(UserCreateAndUpdateDto dto) throws UserAlreadyExistsException;
    UserReadDto update(Long id, UserCreateAndUpdateDto dto);
    boolean delete(Long id);

    UserReadDto findByUsername(String username);

    byte[] getProfileImage(Long id) throws DataNotFoundException;

    boolean updatePassword(String newPassword, String email);
}