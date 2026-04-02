package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.dto.CategoryResponseDTO;
import com.think4tech.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);
    Optional<Category> findBySlug(String slug);

}
