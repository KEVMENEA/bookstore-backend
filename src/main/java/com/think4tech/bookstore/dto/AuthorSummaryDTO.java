package com.think4tech.bookstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorSummaryDTO {
    private String name;
    private String imageUrl;
}