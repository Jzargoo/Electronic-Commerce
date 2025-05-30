package com.jzargo.api.rest.controller;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.services.UserService;
import com.jzargo.shared.model.UserCreateAndUpdateDto;
import com.jzargo.shared.model.UserReadDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@Data
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user by ID
    @GetMapping("/find")
    public ResponseEntity<UserReadDto> findByUsername(@RequestParam String username) throws DataNotFoundException {
        UserReadDto userOpt = userService.findByUsername(username);
        return ResponseEntity.ok(userOpt);
    }
    // Update user information
    @PutMapping
    public ResponseEntity<UserReadDto> update( @RequestBody UserCreateAndUpdateDto dto,Authentication auth) {
        Long id = Long.valueOf(
                ((Jwt) auth.getPrincipal())
                        .getSubject());
        UserReadDto updatedUser = null;
        try {
            updatedUser = userService.update(id, dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping()
    public ResponseEntity<Void> delete(Authentication auth){
        Long id = Long.valueOf(
                ((Jwt) auth.getPrincipal())
                        .getSubject());
        boolean deleted = userService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/icon")


    // Get user profile image
    @GetMapping("/profileImage")
    public ResponseEntity<byte[]> getProfileImage() throws DataNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ||
                !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = Long.valueOf(
                ((Jwt) authentication.getPrincipal()).getSubject()
        );
        byte[] image = userService.getProfileImage(id);
        if (image == null) {
            throw new DataNotFoundException("Profile image not found for user with ID " + id);
        }
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }
}
