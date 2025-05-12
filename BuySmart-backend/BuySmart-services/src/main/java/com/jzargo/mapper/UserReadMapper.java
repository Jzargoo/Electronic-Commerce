package com.jzargo.mapper;

import com.jzargo.entity.User;
import com.jzargo.shared.model.UserReadDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .phone(object.getPhone())
                .createdTime(object.getCreatedTime()==null?
                        LocalDate.now():object.getCreatedTime()
                )
                .username(object.getUsername())
                .OwnProductIds(object.getOwnProductIds())
                .build();
    }
}
