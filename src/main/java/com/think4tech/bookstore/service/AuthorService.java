package com.think4tech.bookstore.service;

import com.think4tech.bookstore.dto.AuthorRequestDTO;
import com.think4tech.bookstore.dto.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {
    AuthorResponseDTO create(AuthorRequestDTO dto);
    List<AuthorResponseDTO> getAll();
    AuthorResponseDTO getById(Long id);
    AuthorResponseDTO update(Long id, AuthorRequestDTO dto);
    void delete(Long id);
}
