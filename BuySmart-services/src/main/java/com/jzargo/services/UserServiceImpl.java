package com.jzargo.services;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.entity.User;
import com.jzargo.mapper.UserCreateAndUpdateMapper;
import com.jzargo.mapper.UserReadMapper;
import com.jzargo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateAndUpdateMapper userCreateAndUpdateMapper;
    private final ImageStorageServiceImpl imageStorageServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserReadMapper userReadMapper,
                           UserCreateAndUpdateMapper userCreateAndUpdateMapper,
                           ImageStorageServiceImpl imageStorageServiceImpl, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userReadMapper = userReadMapper;
        this.userCreateAndUpdateMapper = userCreateAndUpdateMapper;
        this.imageStorageServiceImpl = imageStorageServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Override
    public UserReadDto create(UserCreateAndUpdateDto dto) {


        return Optional.ofNullable(userCreateAndUpdateMapper.map(dto))
                .map(user -> {
                    user.setProfileImage(
                            imageStorageServiceImpl.storeUserFile(dto.getProfileImage())
                    );
                    user.setPassword(
                            passwordEncoder.encode(dto.getPassword())
                    );
                    return userRepository.saveAndFlush(user);
                })
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Override
    public UserReadDto update(Long id, UserCreateAndUpdateDto dto) {

        User old = userRepository.findById(id).orElseThrow();
        return Optional.ofNullable(userCreateAndUpdateMapper.map(dto, old))
                .map(user -> {
                    user.setProfileImage(
                        imageStorageServiceImpl.storeUserFile(dto.getProfileImage())
                    );
                    return userRepository.saveAndFlush(user);
                })
                .map(userReadMapper::map)
                .orElseThrow();

    }

    @Override
    public boolean delete(Long id) {

        User old = userRepository.findById(id).orElseThrow();
        userRepository.delete(old);
        boolean exist = userRepository.existsById(id);

        if (
                !(exist && old.getProfileImage()
                        .equals(System.getenv("DUMMY")))
        ) {
            imageStorageServiceImpl.deleteUserFile(old.getProfileImage());
        }

        return exist;
    }

    @Override
    public UserReadDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(User -> new org.springframework.security.core.userdetails.User(
                        User.getUsername(),
                        User.getPassword(),
                        User.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
