package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.PaymentRequest;
import com.think4tech.bookstore.dto.PaymentResponse;
import com.think4tech.bookstore.dto.PaymentStatus;
import com.think4tech.bookstore.entity.*;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.repository.OrderRepository;
import com.think4tech.bookstore.repository.PaymentRepository;
import com.think4tech.bookstore.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;


    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        if(order.getPaymentStatus() == PaymentStatus.PAID){
            throw  new RuntimeException("Order already paid");
        }
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        if(request.getPaymentMethod() == null || request.getPaymentMethod().isBlank()) {
            throw new RuntimeException("Payment method need required");
        }
        if (request.getAmount() == null || request.getAmount().compareTo(order.getTotalAmount()) != 0) {
            throw new RuntimeException("Payment amount does not match order total");
        }
        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentProvider(request.getPaymentProvider());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setAmount(request.getAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        order.addPayment(payment);

        boolean success = true; // mock payment result for now

        if(success) {
            for(OrderItem item : order.getOrderItems()) {
                Book book = item.getBook();

                if(book.getStockQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
                }
                book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
                bookRepository.save(book);
            }
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setOrderStatus(OrderStatus.CONFIRMED);
        }else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.PENDING);
        }
        Order savedOrder = orderRepository.save(order);
        Payment savedPayment = savedOrder.getPayments().get(savedOrder.getPayments().size() - 1);

        return new PaymentResponse(
                savedPayment.getId(),
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedPayment.getPaymentStatus(),
                savedOrder.getOrderStatus(),
                savedPayment.getAmount(),
                savedPayment.getPaidAt(),
                success ? "Payment processed successfully" : "Payment failed"
        );
    }
}
