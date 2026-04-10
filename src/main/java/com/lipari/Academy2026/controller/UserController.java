package com.lipari.Academy2026.controller;

import com.lipari.Academy2026.dto.UserDTO;
import com.lipari.Academy2026.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    // Dipendenze
    private final UserService userService;

    // Metodi CRUD

    @PostMapping("/new")
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        UserDTO foundUser = this.userService.getUser(id);
        return ResponseEntity.ok(foundUser);
    }

    @PutMapping("/update")
    ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = this.userService.updateUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDTO> deleteUser(@PathVariable UUID id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersList = this.userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }
}

/*
    NOTE DIDATTICHE - [Nome]Controller (Il Vigile delle API)

    Annotazioni LOMBOK/SPRING
    - @RestController
      Unisce @Controller e @ResponseBody. Indica che ogni metodo restituisce
      direttamente dati (JSON).

    - @RequestMapping("[Path-Base]")
      Definisce il prefisso comune per tutti gli endpoint di questa classe.

    - @RequiredArgsConstructor
      Genera il costruttore per i campi 'final'. Permette la Constructor Injection
      delle dipendenze, che è più sicura dell'annotazione @Autowired.

    Logica dei Metodi (REST)
    - ResponseEntity<T>
      Un "wrapper" che permette di controllare il corpo della risposta,
      gli Header e lo Status Code (200 OK, 201 Created, ecc.).

    - @PathVariable vs @RequestBody vs @RequestParam
      1. @PathVariable: Estrae dati dall'URL (es: /id). Si usa per gli ID.
      2. @RequestBody: Converte il JSON in arrivo nel corpo della richiesta in un oggetto Java.
      3. @RequestParam: Cerca dopo il ? nell'URL. Si usa per filtrare una lista o gestire la paginazione.

    - @Valid (Attivazione)
      Fa scattare i controlli definiti nel DTO. Se i dati nel Body sono errati,
      il metodo non viene eseguito e viene restituito un errore al client.

    Annotazioni Verbi HTTP (Mapping)
    - @PostMapping: Usato per la CREAZIONE di risorse.
    - @GetMapping: Usato per il RECUPERO (lettura) di risorse.
    - @PutMapping: Usato per l'AGGIORNAMENTO completo di una risorsa esistente.
    - @DeleteMapping: Usato per la CANCELLAZIONE di una risorsa.

    Gestione delle Risposte (ResponseEntity & HttpStatus)

    SUCCESS (2xx - Operazione riuscita)
    - ResponseEntity.ok(entita) [200]
      Successo generico. Si usa per GET (dati trovati) e PUT (aggiornamento eseguito).

    - ResponseEntity.status(HttpStatus.CREATED).body(entitaCreata) [201]
      Si usa nelle POST. Conferma che la risorsa è stata creata con successo nel database.

    - ResponseEntity.noContent().build() [204]
      Si usa nelle DELETE. Conferma l'eliminazione riuscita;
      l'operazione è OK ma non c'è contenuto da restituire.

    ERRORE CLIENT (4xx - L'utente ha sbagliato qualcosa)
    - 400 Bad Request: Dati non validi o malformati (es. fallimento dei vincoli @Valid).
    - 401 Unauthorized: Utente non autenticato. "Non so chi sei".
    - 403 Forbidden: Utente autenticato ma senza permessi. "So chi sei ma non puoi farlo".
    - 404 Not Found: La risorsa richiesta (ID) non esiste nel database.
    - 409 Conflict: L'operazione contrasta con dati esistenti (es. email già in uso).

    ERRORE SERVER (5xx - Problema tecnico interno)
    - 500 Internal Server Error: indica "fallimento totale". Indica un bug imprevisto
      nel codice o il database irraggiungibile.

-----------
*/
