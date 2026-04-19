package com.think4tech.bookstore.controller;

import com.think4tech.bookstore.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/my-books")
@RequiredArgsConstructor
public class UserBookController {

    private final UserBookService userBookService;

    @PostMapping("/{bookId}/claim-free")
    public ResponseEntity<Void> claimFreeBook(@PathVariable Long bookId) {
        userBookService.claimFreeBook(bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookId}/read")
    public ResponseEntity<?> readBook(@PathVariable Long bookId) {
        String url = userBookService.readBook(bookId);
        return ResponseEntity.ok(Map.of("readerUrl", url));
    }
}