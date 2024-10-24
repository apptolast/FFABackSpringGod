package com.ffa.back.services;


import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public Mono<ResponseEntity<String>> register(String uid, String email) {
        // Verificar si el usuario ya existe
        return userRepository.findByFirebaseUuid(uid)
                .flatMap(existingUser -> {
                    // El usuario ya existe
                    return Mono.just(buildUserExistsResponse());
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // El usuario no existe, crear uno nuevo
                    User newUser = new User();
                    newUser.setFirebase_uuid(uid);
                    newUser.setEmail(email);
                    newUser.setProvider("firebase");

                    // Asignar idioma por defecto o implementar lógica para obtener el idioma
                    Language language = findOrCreateLanguage("en");
                    newUser.setLanguage(language);

                    userRepository.save(newUser);

                    return Mono.just(ResponseEntity.status(201).body("User registered successfully"));
                }));
    }

    public Mono<ResponseEntity<String>> login(String uid, String email) {
        // Verificar si el usuario existe
        return userRepository.findByFirebaseUuid(uid)
                .flatMap(existingUser -> {
                    // Usuario encontrado
                    return Mono.just(ResponseEntity.ok("User logged in successfully"));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // Usuario no encontrado
                    return Mono.just(ResponseEntity.status(404).body("User not found"));
                }));
    }

    private Language findOrCreateLanguage(String languageName) {
        if (languageName == null || languageName.isEmpty()) {
            languageName = "en"; // Idioma predeterminado
        }
        Optional<Language> language = languageRepository.findByLanguage(languageName).blockOptional();
        if (language.isEmpty()) {
            language = Optional.of(languageRepository.save(new Language(languageName)));
        }
        return language.orElse(null);
    }

    private ResponseEntity<String> buildUserExistsResponse() {
        return ResponseEntity.badRequest().body("User already exists, please login.");
    }
}
