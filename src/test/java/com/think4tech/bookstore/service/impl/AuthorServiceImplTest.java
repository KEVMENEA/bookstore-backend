package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.AuthorRequestDTO;
import com.think4tech.bookstore.dto.AuthorResponseDTO;
import com.think4tech.bookstore.entity.Author;
import com.think4tech.bookstore.mapper.AuthorMapper;
import com.think4tech.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void updateCopiesImageUrlAndReturnsIt() {
        Author existing = new Author();
        existing.setId(7L);
        existing.setName("Old name");

        AuthorRequestDTO request = new AuthorRequestDTO(
                "Herbert Schildt",
                "Java programming author",
                "https://example.com/herbert.jpg"
        );

        when(authorRepository.findById(7L)).thenReturn(Optional.of(existing));
        doAnswer(invocation -> {
            AuthorRequestDTO dto = invocation.getArgument(0);
            Author author = invocation.getArgument(1);
            author.setName(dto.getName());
            author.setBio(dto.getBio());
            author.setImageUrl(dto.getImageUrl());
            return null;
        }).when(authorMapper).updateEntityFromDto(request, existing);
        when(authorRepository.save(existing)).thenReturn(existing);
        when(authorMapper.toResponse(existing)).thenAnswer(invocation -> new AuthorResponseDTO(
                existing.getId(),
                existing.getName(),
                existing.getBio(),
                existing.getImageUrl()
        ));

        AuthorResponseDTO response = authorService.update(7L, request);

        verify(authorMapper).updateEntityFromDto(request, existing);
        assertThat(response.getImageUrl()).isEqualTo("https://example.com/herbert.jpg");
    }
}
