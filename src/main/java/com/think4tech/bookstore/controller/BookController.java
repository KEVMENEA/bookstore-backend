package com.think4tech.bookstore.controller;

import com.think4tech.bookstore.dto.BookDetailResponse;
import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.dto.PageDTO;
import com.think4tech.bookstore.mapper.BookMapper;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.service.BookService;
import com.think4tech.bookstore.spec.BookFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Public Endpoint
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can create books
    public BookResponse create(@RequestBody BookRequest request) {
        return bookService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse update(@PathVariable Long id, @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete books
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping
    public PageDTO<BookResponse> getAllBooks(
            @ModelAttribute BookFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        return bookService.getAllBooks(filter, page, size, sort);
    }

    @GetMapping("/{id}")
    public BookDetailResponse getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/slug/{slug}")
    public BookDetailResponse getBySlug(@PathVariable String slug) {
        return bookService.getBySlug(slug);
    }
}
