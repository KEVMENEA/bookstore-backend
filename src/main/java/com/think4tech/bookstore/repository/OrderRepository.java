package com.think4tech.bookstore.repository;

import com.think4tech.bookstore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findWithItemsById(Long id);
    List<Order> findByUserId(Long userId);
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
