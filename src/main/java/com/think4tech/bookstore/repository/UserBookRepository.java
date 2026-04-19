package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);
    List<UserBook> findByUserId(Long userId);

}
