package com.think4tech.bookstore.service.impl;


import com.think4tech.bookstore.dto.BookDetailResponse;
import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.dto.PageDTO;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import com.think4tech.bookstore.entity.Author;
import com.think4tech.bookstore.entity.Category;
import com.think4tech.bookstore.mapper.BookMapper;
import com.think4tech.bookstore.repository.AdminRepository;
import com.think4tech.bookstore.repository.AuthorRepository;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.repository.CategoryRepository;
import com.think4tech.bookstore.service.BookService;
import com.think4tech.bookstore.service.util.PageUtil;
import com.think4tech.bookstore.spec.BookFilter;
import com.think4tech.bookstore.spec.BookSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AdminRepository adminRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse create(BookRequest request) {
        Admin admin = adminRepository.findById(request.getCreatedByAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Book book = bookMapper.toEntity(request, admin);
        // ✅ SET AUTHORS
        if (request.getAuthorIds() != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }

        // ✅ SET CATEGORIES
        if (request.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            book.setCategories(categories);
        }

        Book saved = bookRepository.save(book);


        return bookMapper.toResponse(saved);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookMapper.updateEntityFromRequest(request, existing);

        if (request.getAuthorIds() != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            existing.setAuthors(authors);
        }

        if (request.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            existing.setCategories(categories);
        }


        Book updated = bookRepository.save(existing);
        return bookMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(existing);
    }

    @Override
    public PageDTO<BookResponse> getAllBooks(BookFilter filter, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Page<Book> books = bookRepository.findAll(BookSpec.filter(filter), pageable);
        Page<BookResponse> responsePage = books.map(bookMapper::toResponse);
        return PageUtil.toPageDTO(responsePage);
    }

    private Sort buildSort(String sort) {
        if(sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        String[] parts = sort.split(",");
        String sortField = parts[0].trim();
        String direction = parts.length > 1? parts[1].trim(): "asc";

        return "desc".equalsIgnoreCase(direction)
                ? Sort.by(Sort.Direction.DESC, sortField)
                : Sort.by(Sort.Direction.ASC, sortField);
    }

    @Override
    public BookDetailResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toDetailResponse(book);
    }

    @Override
    public BookDetailResponse getBySlug(String slug) {
        Book book = bookRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toDetailResponse(book);
    }
}
