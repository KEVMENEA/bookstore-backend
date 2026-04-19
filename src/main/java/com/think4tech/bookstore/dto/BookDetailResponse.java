package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class BookDetailResponse {

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
    private List<String> authors;
    private List<String> categories;
}