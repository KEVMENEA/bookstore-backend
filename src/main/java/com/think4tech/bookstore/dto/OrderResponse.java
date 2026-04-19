package com.think4tech.bookstore.dto;

import com.think4tech.bookstore.entity.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long orderId;
    private String orderNumber;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String currency;
    private String orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
    private List<OrderPaymentResponse> payments;
}
