package com.jzargo.services;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.entity.User;
import com.jzargo.mapper.UserCreateAndUpdateMapper;
import com.jzargo.mapper.UserReadMapper;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateAndUpdateMapper userCreateAndUpdateMapper;
    private final ImageStorageServiceImpl imageStorageServiceImpl;

    public UserServiceImpl(UserRepository userRepository, UserReadMapper userReadMapper,
                           UserCreateAndUpdateMapper userCreateAndUpdateMapper,
                           ImageStorageServiceImpl imageStorageServiceImpl) {

        this.userRepository = userRepository;
        this.userReadMapper = userReadMapper;
        this.userCreateAndUpdateMapper = userCreateAndUpdateMapper;
        this.imageStorageServiceImpl = imageStorageServiceImpl;

    }

    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Override
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map).toList();
    }

    @Override
    public UserReadDto create(UserCreateAndUpdateDto dto) {


        return Optional.ofNullable(userCreateAndUpdateMapper.map(dto))
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
}
