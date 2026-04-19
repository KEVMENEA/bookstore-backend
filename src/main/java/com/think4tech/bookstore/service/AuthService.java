package com.think4tech.bookstore.service;

import com.think4tech.bookstore.auth.LoginRequest;
import com.think4tech.bookstore.auth.LoginResponse;
import com.think4tech.bookstore.dto.RegisterRequest;
import com.think4tech.bookstore.dto.UserResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    UserResponse register(RegisterRequest request);
}
