package com.think4tech.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestDTO {

    @NotBlank(message = "Author name is required")
    private String name;
    private String bio;
    private String imageUrl;
}
