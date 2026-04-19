package com.think4tech.bookstore.controller;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;
import com.think4tech.bookstore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AdminRequest request) {
        return ResponseEntity.ok(adminService.create(request));
    }

    // listAll
    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAll() {
        List<AdminResponse> admins = adminService.getAll();
        return ResponseEntity.ok(admins);
    }
    // DeleteByid
    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        adminService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
    // Update
    @PutMapping({"/{id}"})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdminRequest request) {
        AdminResponse adminUpdated = adminService.update(id, request);
        return ResponseEntity.ok(adminUpdated);
    }
}
