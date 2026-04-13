package com.lipari.Academy2026.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEntryResponseDTO(
        UUID id,
        ProductDTO product,
        Integer quantity,
        BigDecimal price,
        BigDecimal total
) {
}

/*
    NOTE DIDATTICHE

    - record
      Classe immutabile per trasportare i dati della singola riga di un ordine.

    OMISSIONE DI OrderResponseDTO order

    - Se OrderResponseDTO contiene una List<OrderEntryResponseDTO> e OrderEntryResponseDTO contiene
      un OrderResponseDTO, il serializzatore JSON (Jackson) entrerà in un loop infinito
      provando a scriverli.
    - Soluzione: Nel DTO della riga d'ordine (Entry) si omette il riferimento
      all'ordine padre, poiché l'informazione è già implicita.

-----------
*/