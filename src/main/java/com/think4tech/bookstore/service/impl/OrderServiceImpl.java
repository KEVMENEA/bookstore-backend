package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.*;
import com.think4tech.bookstore.entity.*;
import com.think4tech.bookstore.exception.ApiException;
import com.think4tech.bookstore.exception.ResourceNotFoundException;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.repository.OrderRepository;
import com.think4tech.bookstore.repository.UserRepository;
import com.think4tech.bookstore.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // =========================
    // CHECKOUT
    // =========================
    @Override
    public OrderResponse checkout(CheckoutRequest request) {

        validateCheckoutRequest(request);

        User user = getCurrentUser();

        Map<Long, Book> bookMap = loadBooks(request);

        Order order = createOrder(user);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItemRequest item : request.getItems()) {
            Book book = bookMap.get(item.getBookId());

            validateStock(book, item.getQuantity());

            OrderItem orderItem = buildOrderItem(order, book, item);

            order.addOrderItem(orderItem);

            subtotal = subtotal.add(orderItem.getTotalPrice());
        }

        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    // =========================
    // GET USER ORDERS
    // =========================
    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    // =========================
    // GET ORDER BY ID
    // =========================
    @Override
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToOrderResponse(order);
    }

    // =========================
    // HELPERS
    // =========================

    private void validateCheckoutRequest(CheckoutRequest request) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Items are required");
        }
    }

    private Map<Long, Book> loadBooks(CheckoutRequest request) {
        List<Long> bookIds = request.getItems()
                .stream()
                .map(CartItemRequest::getBookId)
                .toList();

        return bookRepository.findAllById(bookIds)
                .stream()
                .collect(Collectors.toMap(Book::getId, b -> b));
    }

    private Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setCurrency("USD");
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setDiscountAmount(BigDecimal.ZERO);
        return order;
    }

    private void validateStock(Book book, Integer quantity) {
        if (book == null) {
            throw new ResourceNotFoundException("Book not found");
        }

        if (quantity == null || quantity <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        if (book.getStockQuantity() != null && book.getStockQuantity() < quantity) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Not enough stock for " + book.getTitle());
        }
    }

    private OrderItem buildOrderItem(Order order, Book book, CartItemRequest item) {
        BigDecimal totalPrice = book.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(book);
        orderItem.setQuantity(item.getQuantity());
        orderItem.setUnitPrice(book.getPrice());
        orderItem.setDiscountAmount(BigDecimal.ZERO);
        orderItem.setTotalPrice(totalPrice);

        return orderItem;
    }

    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .bookId(item.getBook().getId())
                        .bookTitle(item.getBook().getTitle())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discountAmount(item.getDiscountAmount())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        List<OrderPaymentResponse> payments = order.getPayments()
                .stream()
                .map(p -> OrderPaymentResponse.builder()
                        .paymentId(p.getId())
                        .paymentMethod(p.getPaymentMethod())
                        .paymentProvider(p.getPaymentProvider())
                        .transactionReference(p.getTransactionReference())
                        .amount(p.getAmount())
                        .paymentStatus(p.getPaymentStatus())
                        .paidAt(p.getPaidAt())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .subtotal(order.getSubtotal())
                .discountAmount(order.getDiscountAmount())
                .totalAmount(order.getTotalAmount())
                .currency(order.getCurrency())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .payments(payments)
                .build();
    }

    private User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}