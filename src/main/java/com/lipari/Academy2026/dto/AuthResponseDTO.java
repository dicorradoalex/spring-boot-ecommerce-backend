package com.lipari.Academy2026.dto;

/**
 * Rappresenta la risposta inviata dal server a seguito di un login riuscito.
 *
 * @param accessToken Il token JWT generato dal server per identificare l'utente.
 * @param tokenType Il tipo di token fornito (solitamente "Bearer").
 */

public record AuthResponseDTO(

        String accessToken,
        String tokenType // "Bearer"
) {
}


/*
    NOTE DIDATTICHE

    - accessToken:
      È il JSON Web Token (JWT) generato dal server.
      Contiene informazioni dell'utente (es. email, ruoli),
      firmate digitalmente e quindi verificabili.
      Il client deve salvarlo e inviarlo ad ogni richiesta.

    - tokenType (Bearer):
      È lo standard di autenticazione HTTP.
      Indica che il token va inviato nell'header:

          Authorization: Bearer <token>

    - Perché "Bearer"?
      È un modello di autenticazione stateless:
      il server non mantiene sessioni,
      ma valida ogni richiesta verificando la firma del token.

    -----------------------------------------
*/