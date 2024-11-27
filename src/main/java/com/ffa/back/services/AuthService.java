package com.ffa.back.services;

import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import com.ffa.back.repositories.LanguageRepository;
import com.ffa.back.repositories.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public Mono<ResponseEntity<String>> register(String uid, String email, FirebaseToken decodedToken) {
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

            // Obtener claims del token
            Map<String, Object> claims = decodedToken.getClaims();

            // Crear nuevo usuario con toda la información del token
            User newUser = new User();
            newUser.setFirebaseUuid(uid);
            newUser.setEmail(email);
            newUser.setProvider("firebase");
            newUser.setRole("USER");

            // Guardamos toda la información del token
            newUser.setSub(decodedToken.getUid());  // El sub es el UID en Firebase
            newUser.setAuthTime((Long) claims.get("auth_time"));
            newUser.setIat((Long) claims.get("iat"));
            newUser.setExp((Long) claims.get("exp"));
            newUser.setEmailVerified(decodedToken.isEmailVerified());

            // Obtener información del proveedor
            @SuppressWarnings("unchecked")
            Map<String, Object> firebaseClaims = (Map<String, Object>) claims.get("firebase");
            if (firebaseClaims != null) {
                String signInProvider = (String) firebaseClaims.get("sign_in_provider");
                newUser.setSignInProvider(signInProvider);
            }

            // Idioma por defecto
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

    public Mono<ResponseEntity<String>> login(String uid, String email, FirebaseToken decodedToken) {
        return Mono.fromCallable(() -> {
            Optional<User> userOpt = userRepository.findByFirebaseUuid(uid);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Actualizar información del token
                Map<String, Object> claims = decodedToken.getClaims();
                user.setAuthTime((Long) claims.get("auth_time"));
                user.setIat((Long) claims.get("iat"));
                user.setExp((Long) claims.get("exp"));
                user.setEmailVerified(decodedToken.isEmailVerified());

                userRepository.save(user);
                return ResponseEntity.ok("Login exitoso");
            } else {
                return ResponseEntity.status(404)
                        .body("Usuario no encontrado");
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}