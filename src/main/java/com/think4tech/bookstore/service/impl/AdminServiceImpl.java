package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.mapper.AdminMapper;
import com.think4tech.bookstore.repository.AdminRepository;
import com.think4tech.bookstore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminResponse create(AdminRequest request) {
        adminRepository.findByEmail(request.getEmail()).ifPresent(a -> {
            throw new RuntimeException("Email already exists");
        });

        Admin admin = adminMapper.toEntity(request);

        Admin saved = adminRepository.save(admin);

        return adminMapper.toResponse(saved);
    }

    @Override
    public List<AdminResponse> getAll() {
        return adminRepository.findAll()
                .stream()
                .map(adminMapper::toResponse)
                .toList();
    }
    @Override
    public void softDelete(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setStatus("INACTIVE");

        adminRepository.save(admin);
    }

    @Override
    public AdminResponse update(Long id, AdminRequest request) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        adminMapper.updateEntity(request, admin);
        return adminMapper.toResponse(adminRepository.save(admin));
    }
}
