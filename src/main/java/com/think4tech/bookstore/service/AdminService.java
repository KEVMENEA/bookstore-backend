package com.think4tech.bookstore.service;

import com.think4tech.bookstore.auth.LoginRequest;
import com.think4tech.bookstore.auth.LoginResponse;
import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;

import java.util.List;

public interface AdminService {
    AdminResponse create(AdminRequest request );
    List<AdminResponse> getAll();
    void softDelete(Long id);
    AdminResponse update(Long id, AdminRequest request);

    interface AuthService {
        LoginResponse login(LoginRequest request);
    }
}
