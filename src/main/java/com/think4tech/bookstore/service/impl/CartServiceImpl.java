package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.CartItemRequest;
import com.think4tech.bookstore.dto.CartResponse;
import com.think4tech.bookstore.entity.Book;
import com.think4tech.bookstore.entity.Cart;
import com.think4tech.bookstore.entity.CartItem;
import com.think4tech.bookstore.entity.User;
import com.think4tech.bookstore.exception.ApiException;
import com.think4tech.bookstore.exception.ResourceNotFoundException;
import com.think4tech.bookstore.mapper.CartMapper;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.repository.CartItemRepository;
import com.think4tech.bookstore.repository.CartRepository;
import com.think4tech.bookstore.repository.UserRepository;
import com.think4tech.bookstore.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            CartMapper cartMapper
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartResponse getCart() {
        User user = getCurrentUser();
        return cartRepository.findByUserId(user.getId())
                .map(cartMapper::toCartResponse)
                .orElseGet(() -> new CartResponse(new ArrayList<>(), 0.0));
    }

    @Override
    public CartResponse addToCart(CartItemRequest request) {
        if (request == null || request.getBookId() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Book ID is required");
        }

        User user = getCurrentUser();

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + request.getBookId()));

        validateBookForCart(book);

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem existingItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId())
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = (existingItem.getQuantity() != null ? existingItem.getQuantity() : 0) + request.getQuantity();
            validateStock(book, newQuantity);
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            validateStock(book, request.getQuantity());

            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUnitPrice(book.getPrice());

            cartItemRepository.save(cartItem);
        }

        Cart updatedCart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + user.getId()));

        return cartMapper.toCartResponse(updatedCart);
    }

    @Override
    public CartResponse updateCart(Long cartItemId, Integer quantity)  {
        if (cartItemId == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cart Item ID is required");
        }
        if (quantity == null || quantity < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Quantity must be at least 1");
        }

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + user.getId()));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));

        validateStock(cartItem.getBook(), quantity);

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        Cart updatedCart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found after update"));

        return cartMapper.toCartResponse(updatedCart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        if (cartItemId == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Cart Item ID is required");
        }

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + user.getId()));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));

        cartItemRepository.delete(cartItem);
    }

    private User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (user.getId() == null) {
             throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Authenticated user has no ID");
        }

        return user;
    }

    private void validateBookForCart(Book book) {
        if (book.getStatus() != null && !"active".equalsIgnoreCase(book.getStatus())) {
            throw new RuntimeException("Book is not available for sale");
        }

        if (Boolean.TRUE.equals(book.getIsFree())) {
            throw new RuntimeException("Free book cannot be added to cart");
        }

        if (book.getPrice() == null || book.getPrice().doubleValue() < 0) {
            throw new RuntimeException("Book price is invalid");
        }
    }

    private void validateStock(Book book, Integer quantity) {
        if (book.getStockQuantity() == null || book.getStockQuantity() < (quantity != null ? quantity : 0)) {
            throw new RuntimeException("Not enough stock for book: " + (book.getTitle() != null ? book.getTitle() : "Unknown"));
        }
    }
}
