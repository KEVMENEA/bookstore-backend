package com.think4tech.bookstore.controller;

import com.think4tech.bookstore.auth.LoginRequest;
import com.think4tech.bookstore.auth.LoginResponse;
import com.think4tech.bookstore.dto.RegisterRequest;
import com.think4tech.bookstore.dto.UserResponse;
import com.think4tech.bookstore.service.AuthService;
import com.think4tech.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }
}