package com.lipari.Academy2026.service.impl;

import com.lipari.Academy2026.dto.UserRegistrationRequestDTO;
import com.lipari.Academy2026.dto.UserResponseDTO;
import com.lipari.Academy2026.entity.UserEntity;
import com.lipari.Academy2026.enums.UserRole;
import com.lipari.Academy2026.exception.AlreadyExistsException;
import com.lipari.Academy2026.exception.ResourceNotFoundException;
import com.lipari.Academy2026.mapper.UserMapper;
import com.lipari.Academy2026.repository.UserRepository;
import com.lipari.Academy2026.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    // Dipendenze -> final per Constructor Injection
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationRequestDTO registrationDTO) {

        // Controllo duplicati
        if (this.userRepository.existsByEmail(registrationDTO.email())) {
            throw new AlreadyExistsException(
                    "Email già registrata: " + registrationDTO.email()
            );
        }

        // Mappatura DTO -> Entity
        UserEntity user = this.userMapper.toEntity(registrationDTO);

        // Hashing della password (con BCrypt)
        String encodedPassword = this.passwordEncoder.encode(registrationDTO.password());
        user.setPassword(encodedPassword);

        // Assegnazione ruolo di default
        user.setRole(UserRole.USER);

        // Salvataggio e risposta
        user = this.userRepository.save(user);
        return this.userMapper.toDto(user);
    }

    @Override
    public UserResponseDTO getUser(UUID id) {
        Optional<UserEntity> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent())
            return this.userMapper.toDto(userOptional.get());
        else
            throw new ResourceNotFoundException("Utente con ID: " + id + " non trovato.");
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        Optional<UserEntity> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent())
            this.userRepository.deleteById(id);
        else throw new ResourceNotFoundException("Utente con ID: " + id + " non trovato.");
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserResponseDTO userResponseDTO) {
        Optional<UserEntity> userOpt = this.userRepository.findById(userResponseDTO.id());
        if(userOpt.isPresent()) {
            UserEntity userToUpdate = userOpt.get();
            this.userMapper.updateEntityFromDto(userResponseDTO, userToUpdate);
            UserResponseDTO savedUser = this.userMapper.toDto(this.userRepository.save(userToUpdate));
            return savedUser;
        } else {
            throw new ResourceNotFoundException("Utente con ID: " + userResponseDTO.id() + "non trovato.");
        }

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> usersList = this.userRepository.findAll();
        return this.userMapper.toDtoList(usersList);
    }

}

/*
    NOTE DIDATTICHE - [Nome]Service (Il Motore della Logica)

    Annotazioni LOMBOK/SPRING (Configurazione)
    - @Service
      Indica che la classe contiene la "Business Logic". Spring la registra nel
      suo contesto per permetterne l'iniezione (DI) nei Controller.

    - @RequiredArgsConstructor
      Abilita la Constructor Injection per le dipendenze (Repository, Mapper).
      Garantisce che il Service sia immutabile e pronto all'uso.

    Spring Security

    - passwordEncoder.encode
      Questo metodo prende la password "in chiaro" e restituisce
      una stringa di circa 60 caratteri (l'hash)


      Gestione delle Transazioni (@Transactional)

    - Concetto Generale:
      Se un metodo fallisce a metà, Spring esegue il Rollback, annullando
      automaticamente ogni modifica fatta fino a quel momento per evitare dati corrotti.
      Va messa nei metodi che modificano il contenuto del db (creazione, modifica o
      cancellazione).

      Per evitare di scrivere @Transactional in tutti i metodi ho adottato questa strategia:

      1. Livello Classe: @Transactional(readOnly = true)
         Imposta l'intero servizio in "sola lettura". Questo ottimizza le
         performance del database e protegge i dati da scritture accidentali.

      2. Livello Metodo: @Transactional
         Viene aggiunto esplicitamente sui metodi di creazione, modifica o
         cancellazione per "aprire" i permessi di scrittura e sovrascrivere
         il limite imposto sulla classe.



    Logica di Business e Integrità
    - Gestione 404 (ResourceNotFoundException)
      Se un ID non esiste, lancia un'eccezione che verrà tradotta in
      un errore 404 per il client.

    - Gestione 409 (AlreadyExistsException)
      Prima di creare nuove risorse, il Service controlla eventuali duplicati
      (es. email o codici univoci) lanciando un'eccezione di conflitto.
-----------
*/
