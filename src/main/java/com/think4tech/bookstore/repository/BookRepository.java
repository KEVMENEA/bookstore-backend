package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
