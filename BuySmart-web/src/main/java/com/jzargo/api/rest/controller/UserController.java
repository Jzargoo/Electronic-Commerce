package com.jzargo.api.rest.controller;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.services.UserService;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    UserReadDto findById(@PathVariable Long id){
        return userService.findById(id)
                .orElseThrow();
    }

    @PostMapping("/registration")
    UserReadDto save(@Validated @RequestBody UserCreateAndUpdateDto dto){
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    UserReadDto update(@PathVariable String id,@RequestBody  UserCreateAndUpdateDto dto){
        return userService.update(Long.valueOf(id),dto);
    }

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable Long id){
        return userService.delete(id);
    }

}
