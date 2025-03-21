package com.jzargo.mapper;

import com.jzargo.dto.UserReadDto;
import com.jzargo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{
    private final CartReadMapper cartReadMapper;
    private final ProductReadMapper productReadMapper;

    public UserReadMapper(CartReadMapper cartReadMapper, ProductReadMapper productReadMapper) {
        this.cartReadMapper = cartReadMapper;
        this.productReadMapper = productReadMapper;
    }

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .phone(object.getPhone())
                .createdTime(object.getCreatedTime()==null?
                        LocalDate.now():object.getCreatedTime()
                )
                .ProfileImage(object.getProfileImage())
                .username(object.getUsername())
                .OwnProducts(
                        object.getOwnProducts().stream()
                                .map(productReadMapper::map)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
