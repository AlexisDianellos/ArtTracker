package com.artgallery.tracker.controller;

import com.artgallery.tracker.dto.UserDto;
import com.artgallery.tracker.model.User;
import com.artgallery.tracker.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                new UserDto(user.getId(), user.getDisplayName(), user.getEmail())
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> users = userService.allUsers().stream()
                .map(u -> new UserDto(u.getId(), u.getDisplayName(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(users);
    }
}
