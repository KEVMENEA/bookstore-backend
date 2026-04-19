package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.*;
import com.think4tech.bookstore.spec.BookFilter;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
    BookDetailResponse getById(Long id);
    BookDetailResponse getBySlug(String slug);
    PageDTO<BookResponse> getAllBooks(BookFilter filter, int page, int size, String sort);
}
