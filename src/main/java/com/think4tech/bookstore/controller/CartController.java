package com.think4tech.bookstore.controller;


import com.think4tech.bookstore.dto.CartItemRequest;
import com.think4tech.bookstore.dto.CartResponse;
import com.think4tech.bookstore.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            @Valid @RequestBody CartItemRequest request) {

        CartResponse response = cartService.addToCart(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable("id") Long id,
            @Valid @RequestBody CartItemRequest request) {

        CartResponse response = cartService.updateCart(id, request.getQuantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("id") Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }



}
