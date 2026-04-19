package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndDeletedAtIsNull(String email);


}
