package com.think4tech.bookstore.dto;

import com.think4tech.bookstore.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String orderNumber;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private String message;

    public PaymentResponse(Long paymentId, Long orderId, String orderNumber, PaymentStatus paymentStatus, OrderStatus orderStatus, BigDecimal amount, LocalDateTime paidAt, String message) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.amount = amount;
        this.paidAt = paidAt;
        this.message = message;
    }
}
