package com.think4tech.bookstore.spec;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {
    private String keyword;
    private String category;
    private String author;
}
