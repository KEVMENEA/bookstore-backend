package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.AdminRequest;
import com.think4tech.bookstore.dto.AdminResponse;
import com.think4tech.bookstore.entity.Admin;
import org.hibernate.annotations.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "id", ignore = true)
    Admin toEntity(AdminRequest request);

    AdminResponse toResponse(Admin admin);

    @Mapping(target = "id", ignore = true)
    void updateEntity(AdminRequest request, @MappingTarget Admin admin);

}