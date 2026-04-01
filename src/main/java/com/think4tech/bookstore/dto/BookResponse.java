package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookResponse {

    private Long bookId;
    private String title;
    private String slug;
    private String description;
    private String isbn;
    private String language;
    private String coverImageUrl;
    private String digitalFileUrl;
    private BigDecimal price;
    private Boolean isFree;
    private Integer stockQuantity;
    private String format;
    private String status;
    private Double averageRating;
    private Integer totalReviews;
    private Long createdByAdminId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}