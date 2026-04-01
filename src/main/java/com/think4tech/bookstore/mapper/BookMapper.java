package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toEntity(BookRequest request, Admin admin) {
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setSlug(request.getSlug());
        book.setDescription(request.getDescription());
        book.setIsbn(request.getIsbn());
        book.setLanguage(request.getLanguage());
        book.setCoverImageUrl(request.getCoverImageUrl());
        book.setDigitalFileUrl(request.getDigitalFileUrl());
        book.setPrice(request.getPrice());
        book.setIsFree(request.getIsFree());
        book.setStockQuantity(request.getStockQuantity());
        book.setFormat(request.getFormat());
        book.setStatus(request.getStatus());
        book.setAverageRating(request.getAverageRating());
        book.setTotalReviews(request.getTotalReviews());
        book.setCreatedByAdmin(admin);

        return book;
    }

    public void updateEntity(Book book, BookRequest request) {

        book.setTitle(request.getTitle());
        book.setSlug(request.getSlug());
        book.setDescription(request.getDescription());
        book.setIsbn(request.getIsbn());
        book.setLanguage(request.getLanguage());
        book.setCoverImageUrl(request.getCoverImageUrl());
        book.setDigitalFileUrl(request.getDigitalFileUrl());
        book.setPrice(request.getPrice());
        book.setIsFree(request.getIsFree());
        book.setStockQuantity(request.getStockQuantity());
        book.setFormat(request.getFormat());
        book.setStatus(request.getStatus());
        book.setAverageRating(request.getAverageRating());
        book.setTotalReviews(request.getTotalReviews());
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .slug(book.getSlug())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .language(book.getLanguage())
                .coverImageUrl(book.getCoverImageUrl())
                .digitalFileUrl(book.getDigitalFileUrl())
                .price(book.getPrice())
                .isFree(book.getIsFree())
                .stockQuantity(book.getStockQuantity())
                .format(book.getFormat())
                .status(book.getStatus())
                .averageRating(book.getAverageRating())
                .totalReviews(book.getTotalReviews())
                .createdByAdminId(
                        book.getCreatedByAdmin() != null
                                ? book.getCreatedByAdmin().getId()
                                : null
                )
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}