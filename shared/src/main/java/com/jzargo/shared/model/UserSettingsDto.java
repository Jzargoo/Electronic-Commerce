package com.jzargo.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDto {
    private String language;
    private String currency;
    private boolean notificationsEnabled;
    private String theme;
    private Long userId;
}
