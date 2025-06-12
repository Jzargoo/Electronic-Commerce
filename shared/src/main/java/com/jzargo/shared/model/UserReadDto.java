package com.jzargo.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserReadDto {
    private Long id;
    private String phone;
    private String email;
    private List<Integer> OwnProductIds;
    private LocalDate createdTime;
    private String username;
}
