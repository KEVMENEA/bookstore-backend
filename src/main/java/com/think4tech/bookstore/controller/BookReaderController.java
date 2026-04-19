//package com.think4tech.bookstore.controller;
//
//import com.think4tech.bookstore.entity.Book;
//import com.think4tech.bookstore.serivce.BookService;
//import com.think4tech.bookstore.serivce.FileStorageService;
//import jakarta.annotation.Resource;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.nio.file.Path;
//
//@RestController
//@RequestMapping("/api/books")
//public class BookReaderController {
//
//    private final BookService bookService;
//    private final FileStorageService fileStorageService;
////    private final OrderService orderService;
//
//    public BookReaderController(BookService bookService,
//                                FileStorageService fileStorageService) {
//        this.bookService = bookService;
//        this.fileStorageService = fileStorageService;
////        this.orderService = orderService;
//    }
//
//    @GetMapping("/{bookId}/read")
//    public ResponseEntity<Resource> readBook(@PathVariable Long bookId,
//                                             Authentication authentication) throws Exception {
//        Book book = bookService.findById(bookId);
//
//        if (!book.isFree()) {
//            if (authentication == null) {
//                throw new RuntimeException("Please login first");
//            }
//
//            String email = authentication.getName();
//            boolean purchased = orderService.hasUserPurchasedBook(email, bookId);
//
//            if (!purchased) {
//                throw new RuntimeException("You must buy this book first");
//            }
//        }
//
//        Path filePath = fileStorageService.getFilePath(book.getDigitalFileName());
//        Resource resource = new UrlResource(filePath.toUri());
//
//        if (!resource.exists() || !resource.isReadable()) {
//            throw new RuntimeException("Book file not found");
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
//}