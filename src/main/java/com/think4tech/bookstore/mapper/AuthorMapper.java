package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.AuthorRequestDTO;
import com.think4tech.bookstore.dto.AuthorResponseDTO;
import com.think4tech.bookstore.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorRequestDTO dto);
    AuthorResponseDTO toResponse(Author entity);
    void updateEntityFromDto(AuthorRequestDTO dto, @MappingTarget Author entity);
}

