package com.lipari.Academy2026.service;

import com.lipari.Academy2026.dto.CategoryRequestDTO;
import com.lipari.Academy2026.dto.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDTO getCategory(UUID id);

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    void deleteCategory(UUID id);

    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, UUID id);

    List<CategoryResponseDTO> getAllCategories();
}
