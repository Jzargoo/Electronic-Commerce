package com.jzargo.services;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.UserAlreadyExistsException;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.jzargo.shared.model.UserReadDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserReadDto> findById(Long id);
    UserReadDto create(UserCreateAndUpdateDto dto) throws UserAlreadyExistsException, IOException;
    UserReadDto update(Long id, UserCreateAndUpdateDto dto) throws IOException;
    boolean delete(Long id);

    UserReadDto findByUsername(String username);


    boolean updatePassword(String newPassword, String email);

    byte[] getProfileImage(Long id) throws DataNotFoundException;

}