package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.dto.CategoryRequestDTO;
import com.lipari.Academy2026.dto.CategoryResponseDTO;
import com.lipari.Academy2026.entity.CategoryEntity;
import com.lipari.Academy2026.exception.ResourceNotFoundException;
import com.lipari.Academy2026.mapper.CategoryMapper;
import com.lipari.Academy2026.repository.CategoryRepository;
import com.lipari.Academy2026.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO getCategory(UUID id) {
        Optional<CategoryEntity> categoryOpt = this.categoryRepository.findById(id);
        
        if (!categoryOpt.isPresent())
            throw new ResourceNotFoundException("Categoria con ID: " + id + " non trovata");

        return this.categoryMapper.toDto(categoryOpt.get());
    }

    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        // Converto DTO in Entity
        CategoryEntity category = this.categoryMapper.toEntity(requestDTO);
        
        // Salvo e restituisco
        CategoryEntity savedCategory = this.categoryRepository.save(category);
        return this.categoryMapper.toDto(savedCategory);
    }

    @Transactional
    @Override
    public void deleteCategory(UUID id) {
        Optional<CategoryEntity> categoryOpt = this.categoryRepository.findById(id);
        
        if (!categoryOpt.isPresent())
            throw new ResourceNotFoundException("Categoria con ID: " + id + " non trovata");
        this.categoryRepository.delete(categoryOpt.get());
    }

    @Transactional
    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO requestDTO, UUID id) {
        // Recupero la categoria dal db
        Optional<CategoryEntity> categoryOpt = this.categoryRepository.findById(id);
        
        if (!categoryOpt.isPresent())
            throw new ResourceNotFoundException("Categoria con ID: " + id + " non trovata");

        CategoryEntity categoryToUpdate = categoryOpt.get();

        // Aggiorno i campi tramite il mapper
        this.categoryMapper.updateEntityFromRequest(requestDTO, categoryToUpdate);

        // Salvo e restituisco
        CategoryEntity savedCategory = this.categoryRepository.save(categoryToUpdate);
        return this.categoryMapper.toDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<CategoryEntity> categoriesList = this.categoryRepository.findAll();
        return this.categoryMapper.toDtoList(categoriesList);
    }
}