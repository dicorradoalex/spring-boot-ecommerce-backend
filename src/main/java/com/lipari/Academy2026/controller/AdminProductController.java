package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.ProductRequestDTO;
import com.lipari.Academy2026.dto.ProductResponseDTO;
import com.lipari.Academy2026.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    /**
     * Crea un nuovo prodotto nel catalogo.
     */
    @PostMapping("/new")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO createdProduct = this.productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Aggiorna i dati di un prodotto esistente.
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        
        ProductResponseDTO modifiedProduct = this.productService.updateProduct(productRequestDTO, id);
        return ResponseEntity.ok(modifiedProduct);
    }

    /**
     * Elimina un prodotto dal catalogo tramite ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
