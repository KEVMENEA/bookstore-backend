package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Page<UserResponse> getAllUsers(int page, int size);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
}
