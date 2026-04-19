package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findBySlug(String slug);
    boolean existsBySlug(String slug);

}
