package com.think4tech.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    private String name;

    @NotBlank(message = "Slug name is required")
    private String slug;

    private Long parentCategoryId;
}
