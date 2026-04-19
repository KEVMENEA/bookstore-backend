package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemResponse {

    private Long bookId;
    private String bookTitle;
    private String author;
    private String imageUrl;

    private Integer quantity;
    private Double price;
    private Double totalPrice;

}