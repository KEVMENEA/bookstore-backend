package com.think4tech.bookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookRequest {

    @NotBlank(message = "Book Title is required.")
    private String title;

    @NotBlank(message = "Book Slug is required.")
    private String slug;
    private String description;
    private String isbn;
    private String language;
    private String coverImageUrl;
    private String digitalFileUrl;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Please specify whether the book is free.")
    private Boolean isFree;

    @Min(value  = 0, message = "Please specify the stock quantity.")
    private Integer stockQuantity;
    private String format;
    private String status;
    private Double averageRating;
    private Integer totalReviews;

    @NotNull(message = "Created by admin ID is required.")
    private Long createdByAdminId;

    private List<Long> authorIds;
    private List<Long> categoryIds;
}