package com.jzargo.mapper;

import com.jzargo.entity.UserSettings;
import com.jzargo.repository.UserRepository;
import com.jzargo.shared.model.UserSettingsDto;
import org.springframework.stereotype.Component;

@Component
public class UserSettingsCreateAndUpdateMapper implements Mapper<UserSettingsDto, UserSettings>{
    private final UserRepository userRepository;

    public UserSettingsCreateAndUpdateMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSettings map(UserSettingsDto object) {
        return UserSettings.builder()
                .currency(object.getCurrency())
                .theme(object.getTheme())
                .user(
                        userRepository.findById(
                                object.getUserId()).orElseThrow()
                )
                .language(object.getLanguage())
                .notificationsEnabled(object.isNotificationsEnabled())
                .build();
    }
}
