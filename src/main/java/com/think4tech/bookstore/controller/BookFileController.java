package com.think4tech.bookstore.controller;


import com.think4tech.bookstore.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class BookFileController {

    private final FileStorageService fileStorageService;

    public BookFileController(FileStorageService fileStorageService) {

        this.fileStorageService = fileStorageService;
    }
    @PostMapping(value = "/upload-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadBookFile(@RequestParam("file") MultipartFile file) throws Exception {
        String storedFilename = fileStorageService.storeFile(file);

        Map<String, String> response = new HashMap<>();
        response.put("fileName", storedFilename);
        return ResponseEntity.ok(response);
    }

}
