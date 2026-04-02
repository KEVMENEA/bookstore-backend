package com.think4tech.bookstore.serivce.impl;


import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import com.think4tech.bookstore.mapper.BookMapper;
import com.think4tech.bookstore.repository.AdminRepository;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.serivce.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AdminRepository adminRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse create(BookRequest request) {
        Admin admin = adminRepository.findById(request.getCreatedByAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Book book = bookMapper.toEntity(request, admin);
        Book saved = bookRepository.save(book);

        return bookMapper.toResponse(saved);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookMapper.updateEntityFromRequest(request, existing);

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
    public Page<BookResponse> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse getBySlug(String slug) {
        Book book = bookRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return bookMapper.toResponse(book);
    }
}