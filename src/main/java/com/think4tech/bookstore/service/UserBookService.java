package com.think4tech.bookstore.service;


public interface UserBookService {
    String readBook(Long bookId);
    void claimFreeBook(Long bookId);
}
