package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.AuthorSummaryDTO;
import com.think4tech.bookstore.dto.BookDetailResponse;
import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Author;
import com.think4tech.bookstore.entity.Book;
import com.think4tech.bookstore.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;
@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdByAdmin", source = "admin")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookRequest request, Admin admin);

    @Mapping(source = "id", target = "bookId")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "mapAuthorsToSummary")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoriesToNames")
    BookResponse toResponse(Book book);

    @Mapping(source = "id", target = "bookId")
    @Mapping(source = "authors", target = "authors", qualifiedByName = "mapAuthorsToSummary")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoriesToNames")
    BookDetailResponse toDetailResponse(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdByAdmin", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget Book book);

    @Named("mapAuthorsToSummary")
    default List<AuthorSummaryDTO> mapAuthorsToSummary(Collection<Author> authors) {
        if (authors == null) {
            return List.of();
        }
        return authors.stream()
                .map(author -> AuthorSummaryDTO.builder()
                        .name(author.getName())
                        .imageUrl(author.getImageUrl())
                        .build())
                .toList();
    }

    @Named("mapCategoriesToNames")
    default List<String> mapCategoriesToNames(Collection<Category> categories) {
        if (categories == null) {
            return List.of();
        }
        return categories.stream()
                .map(Category::getName)
                .toList();
    }
}