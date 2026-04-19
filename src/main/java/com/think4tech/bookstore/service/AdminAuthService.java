package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;

public interface AdminAuthService {
    AdminResponse login(AdminRequest request) ;
}
