package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Builder
public class CartResponse {
    private List<CartItemResponse> items;
    private Double totalAmount;

    public CartResponse(List<CartItemResponse> items, Double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

}