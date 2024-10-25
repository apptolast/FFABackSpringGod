package com.ffa.back.services;


import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public Mono<ResponseEntity<String>> register(String uid, String email) {
        return Mono.fromCallable(() -> {
            // Verificar si el usuario existe
            Optional<User> existingUser = userRepository.findByFirebaseUuid(uid);

            if (existingUser.isPresent()) {
                if (existingUser.get().getEmail().equals(email)) {
                    return ResponseEntity.badRequest()
                            .body("Usuario ya existe, por favor inicia sesión");
                } else {
                    return ResponseEntity.badRequest()
                            .body("Usuario existe con un email diferente");
                }
            }

            // Crear nuevo usuario
            User newUser = new User();
            newUser.setFirebase_uuid(uid);
            newUser.setEmail(email);
            newUser.setProvider("firebase");
            newUser.setRole("USER");

            // Buscar o crear idioma por defecto
            Language language = languageRepository.findByLanguage("en")
                    .orElseGet(() -> {
                        Language newLanguage = new Language("en");
                        return languageRepository.save(newLanguage);
                    });

            newUser.setLanguage(language);
            userRepository.save(newUser);

            return ResponseEntity.status(201)
                    .body("Usuario registrado correctamente");
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<String>> login(String uid, String email) {
        return Mono.fromCallable(() -> {
            Optional<User> user = userRepository.findByFirebaseUuid(uid);

            if (user.isPresent()) {
                return ResponseEntity.ok("Login exitoso");
            } else {
                return ResponseEntity.status(404)
                        .body("Usuario no encontrado");
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}