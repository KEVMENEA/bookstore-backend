package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderPaymentResponse {

    private Long paymentId;
    private String paymentMethod;
    private String paymentProvider;
    private String transactionReference;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
}