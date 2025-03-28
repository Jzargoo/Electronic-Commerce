package com.jzargo.api.rest.controller;

import com.jzargo.dto.UserCreateAndUpdateDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.services.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Data
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> findById(@PathVariable Long id) throws DataNotFoundException {
        Optional<UserReadDto> userOpt = userService.findById(id);
        return userOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new DataNotFoundException("User with ID " + id + " not found"));
    }

    // Register new user
    @PostMapping("/registration")
    public ResponseEntity<UserReadDto> save(@Validated @RequestBody UserCreateAndUpdateDto dto) {
        UserReadDto createdUser = userService.create(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update user information
    @PutMapping("/{id}")
    public ResponseEntity<UserReadDto> update(@PathVariable Long id, @RequestBody UserCreateAndUpdateDto dto) {
        UserReadDto updatedUser = userService.update(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = userService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get user profile image
    @GetMapping("/profileImage/{id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) throws DataNotFoundException {
        byte[] image = userService.getProfileImage(id);
        if (image == null) {
            throw new DataNotFoundException("Profile image not found for user with ID " + id);
        }
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }
}
