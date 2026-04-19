package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.CheckoutRequest;
import com.think4tech.bookstore.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(CheckoutRequest request);
    OrderResponse getOrderById(Long id, Long currentUserId);
    List<OrderResponse> getUserOrders(Long currentUserId);
}
