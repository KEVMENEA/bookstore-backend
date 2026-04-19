package com.think4tech.bookstore.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadBookResponse {
    private Long bookId;
    private String bookTitle;
    private String secureFileUrl;
}