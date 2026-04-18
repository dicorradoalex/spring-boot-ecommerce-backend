package com.lipari.Academy2026.exception;

/**
 * Eccezione lanciata quando la quantità richiesta di un prodotto
 * supera la disponibilità effettiva in magazzino.
 */
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}
