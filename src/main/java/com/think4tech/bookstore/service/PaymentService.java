package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.PaymentRequest;
import com.think4tech.bookstore.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);

}
