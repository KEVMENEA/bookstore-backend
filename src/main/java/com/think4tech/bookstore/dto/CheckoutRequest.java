package com.think4tech.bookstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    @NotEmpty(message = "Items cannot be empty")
    @Valid
    private List<CartItemRequest> items;
}
