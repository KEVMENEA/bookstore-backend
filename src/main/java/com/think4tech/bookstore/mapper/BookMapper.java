package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.BookRequest;
import com.think4tech.bookstore.dto.BookResponse;
import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdByAdmin", source = "admin")
    @Mapping(target = "status", source = "request.status")
    Book toEntity(BookRequest request, Admin admin);

    BookResponse toResponse(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdByAdmin", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget Book book);
}