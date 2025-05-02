package com.jzargo.shared.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class UserReadDto {
    private Long id;
    private String phone;
    private String email;
    private List<Integer> OwnProductIds;
    private LocalDate createdTime;
    private String username;
}
