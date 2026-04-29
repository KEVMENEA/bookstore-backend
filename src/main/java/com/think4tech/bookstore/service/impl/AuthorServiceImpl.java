package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.AuthorRequestDTO;
import com.think4tech.bookstore.dto.AuthorResponseDTO;
import com.think4tech.bookstore.entity.Author;
import com.think4tech.bookstore.exception.ResourceNotFoundException;
import com.think4tech.bookstore.mapper.AuthorMapper;
import com.think4tech.bookstore.repository.AuthorRepository;
import com.think4tech.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    @Override
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        authorRepository.findByNameIgnoreCase(dto.getName())
                .ifPresent(author -> {
                    throw new RuntimeException("Author already exists");
                });

        Author saved = authorRepository.save(authorMapper.toEntity(dto));
        return authorMapper.toResponse(saved);
    }

    @Override
    public List<AuthorResponseDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseDTO getById(Long id) {
        Author author =  authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found", id));

        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorRequestDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found", id));

        authorMapper.updateEntityFromDto(dto, author);

        return authorMapper.toResponse(authorRepository.save(author));
    }


    @Override
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found", id);
        }
        authorRepository.deleteById(id);
    }

}
