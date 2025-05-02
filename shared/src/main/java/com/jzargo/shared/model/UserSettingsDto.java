package com.jzargo.shared.model;

import lombok.Data;

@Data
public class UserSettingsDto {
    private String language;
    private String currency;
    private boolean notificationsEnabled;
    private String theme;
}
