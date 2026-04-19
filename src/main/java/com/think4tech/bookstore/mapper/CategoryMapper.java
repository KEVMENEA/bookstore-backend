package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.CategoryRequestDTO;
import com.think4tech.bookstore.dto.CategoryResponseDTO;
import com.think4tech.bookstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    Category toEntity(CategoryRequestDTO dto);

    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    CategoryResponseDTO toResponse(Category entity);
}