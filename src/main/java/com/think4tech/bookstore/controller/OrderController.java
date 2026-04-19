package com.think4tech.bookstore.controller;

import com.think4tech.bookstore.dto.CheckoutRequest;
import com.think4tech.bookstore.dto.OrderResponse;
import com.think4tech.bookstore.entity.User;
import com.think4tech.bookstore.repository.UserRepository;
import com.think4tech.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController{

    private final OrderService orderService;
    private final UserRepository userRepository;

    // ✅ Checkout
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest request) {
        OrderResponse response = orderService.checkout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Get all orders (order history)
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders() {
        Long currentUserId = getCurrentUserId();
        return ResponseEntity.ok(orderService.getUserOrders(currentUserId));
    }

    // ✅ Get single order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Long currentUserId = getCurrentUserId();
        return ResponseEntity.ok(orderService.getOrderById(id, currentUserId));
    }

    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }
}
