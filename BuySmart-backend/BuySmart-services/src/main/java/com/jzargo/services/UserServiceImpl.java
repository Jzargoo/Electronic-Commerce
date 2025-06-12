package com.jzargo.services;

import com.jzargo.entity.User;
import com.jzargo.entity.UserSettings;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.UserAlreadyExistsException;
import com.jzargo.mapper.UserCreateAndUpdateMapper;
import com.jzargo.mapper.UserReadMapper;
import com.jzargo.mapper.UserSettingsCreateAndUpdateMapper;
import com.jzargo.mapper.UserSettingsReadMapper;
import com.jzargo.repository.UserRepository;
import com.jzargo.repository.UserSettingsRepository;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.jzargo.shared.model.UserReadDto;
import com.jzargo.shared.model.UserSettingsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateAndUpdateMapper userCreateAndUpdateMapper;
    private final ImageStorageService imageStorageService;
    private final PasswordEncoder passwordEncoder;
    private final UserSettingsRepository userSettingsRepository;
    private final UserSettingsCreateAndUpdateMapper userSettingsCreateAndUpdateMapper;
    private final UserSettingsReadMapper userSettingsReadMapper;

    public UserServiceImpl(UserRepository userRepository, UserReadMapper userReadMapper,
                           UserCreateAndUpdateMapper userCreateAndUpdateMapper,
                           ImageStorageService imageStorageService, PasswordEncoder passwordEncoder, UserSettingsRepository userSettingsRepository, UserSettingsCreateAndUpdateMapper userSettingsCreateAndUpdateMapper, UserSettingsReadMapper userSettingsReadMapper) {

        this.userRepository = userRepository;
        this.userReadMapper = userReadMapper;
        this.userCreateAndUpdateMapper = userCreateAndUpdateMapper;
        this.imageStorageService = imageStorageService;
        this.passwordEncoder = passwordEncoder;
        this.userSettingsRepository = userSettingsRepository;
        this.userSettingsCreateAndUpdateMapper = userSettingsCreateAndUpdateMapper;
        this.userSettingsReadMapper = userSettingsReadMapper;
    }

    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }


    @Override
    public UserReadDto create(UserCreateAndUpdateDto dto) throws UserAlreadyExistsException, IOException {
        User map = userCreateAndUpdateMapper.map(dto);
        map.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );
        map.setProfileImage(
                imageStorageService.storeUserFile(dto.getProfileImage())
        );
        if (userRepository.existsByUsername(map.getUsername()) ||
                (dto.getPhone() != null && userRepository.existsByPhone(dto.getPhone())) ||
                (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail()))) {
            throw new UserAlreadyExistsException();
        }
        User user = userRepository.saveAndFlush(map);
        initializeUserSettingsByDefault(user.getId());
        return userReadMapper.map(user);
    }

    private void initializeUserSettingsByDefault(Long userId) {
        UserSettingsDto build = UserSettingsDto.builder()
                .currency("USD")
                .language("en")
                .userId(userId)
                .theme("black")
                .notificationsEnabled(true)
                .build();

        UserSettings userSettings = userSettingsRepository.saveAndFlush(
                userSettingsCreateAndUpdateMapper.map(build)
        );
    }

    @Override
    public UserReadDto update(Long id, UserCreateAndUpdateDto dto) {

        User old = userRepository.findById(id).orElseThrow();
        return Optional.ofNullable(userCreateAndUpdateMapper.map(dto, old))
                .map(user -> {
                    try {
                        user.setProfileImage(
                            imageStorageService.storeUserFile(dto.getProfileImage())
                        );
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
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
            imageStorageService.deleteUserFile(old.getProfileImage());
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
    public byte[] getProfileImage(Long id) throws DataNotFoundException {
        return imageStorageService.getUserFile(
                userRepository.findById(id)
                        .map(User::getProfileImage)
                        .orElseThrow(DataNotFoundException::new)
        );
    }

    @Override
    public UserSettingsDto findSettingsByUserId(Long l) throws DataNotFoundException {
        return userSettingsRepository.findByUserId(l)
                .map(userSettingsReadMapper::map)
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public UserSettingsDto updateSettings(UserSettingsDto dto) throws DataNotFoundException {
         return Optional.of(userSettingsCreateAndUpdateMapper.map(dto))
                .map(userSettingsRepository::saveAndFlush)
                .map(userSettingsReadMapper::map)
                .orElseThrow(DataNotFoundException::new);
    }


    @Override
    public boolean updatePassword(String newPassword, String email) {
        userRepository.findByEmail(email)
                .map(user-> {
                        user.setPassword(
                                    passwordEncoder.encode(newPassword));
                        return user;
                        }
                ).map(userRepository::saveAndFlush);
        return userRepository.existsByPassword(newPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Granted Authorities: " + user.getAuthorities());
        return user;
    }
}
