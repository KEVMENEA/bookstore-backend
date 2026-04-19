package com.think4tech.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private String paymentMethod;
    private String paymentProvider;
    private String transactionReference;
    private BigDecimal amount;
}
