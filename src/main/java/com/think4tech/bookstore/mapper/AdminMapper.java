package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;
import com.think4tech.bookstore.entity.Admin;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(AdminRequest request) {
        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPasswordHash(request.getPassword()); // later hash it
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());
        return admin;
    }

    public AdminResponse toResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId()) // fix: use correct field
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .status(admin.getStatus())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public void updateEntity(Admin admin, AdminRequest request) {
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());

        if (request.getPassword() != null) {
            admin.setPasswordHash(request.getPassword());
        }
    }
}