package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long > {
    Optional<CartItem> findByIdAndCartId(Long Id, Long CartId);
    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);
}
