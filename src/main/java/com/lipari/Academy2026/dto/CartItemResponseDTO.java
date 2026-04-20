package com.lipari.Academy2026.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Rappresenta una singola riga del carrello nella risposta API.
 */

public record CartItemResponseDTO(
        UUID id,
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {}
