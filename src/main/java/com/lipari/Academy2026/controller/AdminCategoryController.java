package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.CategoryRequestDTO;
import com.lipari.Academy2026.dto.CategoryResponseDTO;
import com.lipari.Academy2026.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    /**
     * Crea una nuova categoria di prodotti.
     */
    @PostMapping("/new")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryDTO) {
        CategoryResponseDTO createdCategory = this.categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    /**
     * Aggiorna il nome di una categoria esistente.
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequestDTO categoryDTO) {
        
        CategoryResponseDTO modifiedCategory = this.categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok(modifiedCategory);
    }

    /**
     * Elimina una categoria dal sistema tramite ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
