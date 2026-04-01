package com.think4tech.bookstore.serivce;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;
import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
    Page<BookResponse> getAll(Pageable pageable);
    BookResponse getById(Long id);
    BookResponse getBySlug(String slug);
}
