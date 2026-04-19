package com.lipari.Academy2026.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rappresenta lo stato completo del carrello restituito all'utente.
 * Contiene l'elenco dei prodotti e il totale complessivo calcolato.
 */

public record CartResponseDTO(

        List<CartItemResponseDTO> items,
        BigDecimal total
) {}
