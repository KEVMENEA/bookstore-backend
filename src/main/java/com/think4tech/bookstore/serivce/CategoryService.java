package com.think4tech.bookstore.serivce;

import com.think4tech.bookstore.dto.CategoryRequestDTO;
import com.think4tech.bookstore.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CategoryRequestDTO dto);
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO getById(Long id);
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);
    void delete(Long id);
    CategoryResponseDTO getBySlug(String slug);

}
