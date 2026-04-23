package com.think4tech.bookstore.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookResponse {

    private Long bookId;
    private String title;
    private String slug;
    private String description;
    private String digitalFileUrl;
    private String coverImageUrl;
    private BigDecimal price;
    private Boolean isFree;
    private Integer stockQuantity;
    private Double averageRating;
    private Integer totalReviews;
    private List<AuthorSummaryDTO> authors;
    private List<String> categories;
}