package com.think4tech.bookstore.service.impl;

import com.think4tech.bookstore.dto.CategoryRequestDTO;
import com.think4tech.bookstore.dto.CategoryResponseDTO;
import com.think4tech.bookstore.entity.Category;
import com.think4tech.bookstore.exception.ResourceNotFoundException;
import com.think4tech.bookstore.mapper.CategoryMapper;
import com.think4tech.bookstore.repository.CategoryRepository;
import com.think4tech.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);

        if (dto.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
            category.setParentCategory(parent);
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", id));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDTO getBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category", slug));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", id));

        if(!category.getSlug().equals(dto.getSlug()) && categoryRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Category already exists");

        }
        if(dto.getParentCategoryId() != null && dto.getParentCategoryId().equals(id)) {
            throw new RuntimeException("Parent category cannot be the same as the current category");

        }
        Category parent = null;
        if (dto.getParentCategoryId() != null) {
            parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found", id));
        }
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setParentCategory(parent);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(categoryRepository.save(updatedCategory));

    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found", id);
        }
        categoryRepository.deleteById(id);


    }


}
