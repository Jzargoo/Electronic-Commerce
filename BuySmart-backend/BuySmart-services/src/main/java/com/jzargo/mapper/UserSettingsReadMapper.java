package com.jzargo.mapper;

import com.jzargo.entity.UserSettings;
import com.jzargo.shared.model.UserSettingsDto;
import org.springframework.stereotype.Component;

@Component
public class UserSettingsReadMapper implements Mapper<UserSettings, UserSettingsDto>{
    @Override
    public UserSettingsDto map(UserSettings object) {
        return UserSettingsDto.builder()
                .currency(object.getCurrency())
                .language(object.getLanguage())
                .theme(object.getTheme())
                .notificationsEnabled(object.isNotificationsEnabled())
                .build();
    }
}
