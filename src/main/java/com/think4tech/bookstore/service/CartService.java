package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.CartItemRequest;
import com.think4tech.bookstore.dto.CartResponse;

public interface CartService {
    CartResponse getCart();
    CartResponse addToCart(CartItemRequest request);
    CartResponse updateCart(Long cartItemId, Integer quantity);
    void removeCartItem(Long cartItemId);
}
