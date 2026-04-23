package com.think4tech.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDTO {

    @NotBlank(message = "Author name is required")
    private String name;
    private String bio;
    private String imageUrl;
}
