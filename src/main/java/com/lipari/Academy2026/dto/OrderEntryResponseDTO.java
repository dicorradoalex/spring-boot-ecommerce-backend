package com.lipari.Academy2026.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEntryResponseDTO(
        UUID id,
        ProductResponseDTO product,
        Integer quantity,
        BigDecimal price,
        BigDecimal total) {
}