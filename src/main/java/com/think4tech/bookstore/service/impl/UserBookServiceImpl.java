package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.config.SecurityUtils;
import com.think4tech.bookstore.entity.Book;
import com.think4tech.bookstore.entity.User;
import com.think4tech.bookstore.entity.UserBook;
import com.think4tech.bookstore.exception.ApiException;
import com.think4tech.bookstore.exception.ResourceNotFoundException;
import com.think4tech.bookstore.repository.BookRepository;
import com.think4tech.bookstore.repository.UserBookRepository;
import com.think4tech.bookstore.repository.UserRepository;
import com.think4tech.bookstore.service.UserBookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserBookServiceImpl implements UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SecurityUtils securityUtils;

    public UserBookServiceImpl(
            UserBookRepository userBookRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            SecurityUtils securityUtils
    ) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public void claimFreeBook(Long bookId) {
        String email = securityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (!Boolean.TRUE.equals(book.getIsFree())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "This book is not free");
        }

        if (book.getDigitalFileUrl() == null || book.getDigitalFileUrl().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Digital file not available");
        }

        if (userBookRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
            return;
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setSource("FREE");
        userBook.setGrantedAt(LocalDateTime.now());
        userBook.setAccessStatus("ACTIVE");

        userBookRepository.save(userBook);
    }

    @Override
    @Transactional
    public String readBook(Long bookId) {
        String email = securityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        validateReadableBook(book);

        UserBook userBook = userBookRepository
                .findByUserIdAndBookId(user.getId(), bookId)
                .orElse(null);

        if (userBook == null) {
            if (Boolean.TRUE.equals(book.getIsFree())) {
                userBook = grantFreeAccess(user, book);
            } else {
                throw new ApiException(HttpStatus.FORBIDDEN, "You do not own this book");
            }
        }

        if (!"ACTIVE".equalsIgnoreCase(userBook.getAccessStatus())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Book access is not active");
        }

        return book.getDigitalFileUrl();
    }

    private void validateReadableBook(Book book) {
        if (book.getDigitalFileUrl() == null || book.getDigitalFileUrl().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Digital file not available");
        }
    }

    private UserBook grantFreeAccess(User user, Book book) {
        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setSource("FREE");
        userBook.setGrantedAt(LocalDateTime.now());
        userBook.setAccessStatus("ACTIVE");
        return userBookRepository.save(userBook);
    }
}