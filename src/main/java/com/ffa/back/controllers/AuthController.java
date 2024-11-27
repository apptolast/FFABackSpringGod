package com.ffa.back.controllers;

import com.ffa.back.services.AuthService;
import com.ffa.back.services.FirebaseAuthService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("familyfilmapp/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @CrossOrigin
    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(
            @RequestHeader("Authorization") String authHeader,
            @AuthenticationPrincipal Mono<Authentication> authenticationMono) {
        return authenticationMono.flatMap(authentication -> {
            if (authentication == null || !authentication.isAuthenticated()) {
                return Mono.just(ResponseEntity.status(401).body("Unauthorized"));
            }

            try {
                // Obtener y verificar el token
                String token = authHeader.replace("Bearer ", "");
                FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

                // Extraer información del usuario
                String uid = authentication.getName();
                String email = decodedToken.getEmail();

                // Llamar al servicio de autenticación
                return authService.login(uid, email, decodedToken);
            } catch (Exception e) {
                return Mono.just(ResponseEntity.status(401).body("Token inválido"));
            }
        });
    }

    @CrossOrigin
    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(
            @RequestHeader("Authorization") String authHeader,
            @AuthenticationPrincipal Mono<Authentication> authenticationMono) {
        return authenticationMono.flatMap(authentication -> {
            if (authentication == null || !authentication.isAuthenticated()) {
                return Mono.just(ResponseEntity.status(401).body("Unauthorized"));
            }

            try {
                // Obtener y verificar el token
                String token = authHeader.replace("Bearer ", "");
                FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

                // Extraer información del usuario
                String uid = authentication.getName();
                String email = decodedToken.getEmail();

                // Llamar al servicio de registro
                return authService.register(uid, email, decodedToken);
            } catch (Exception e) {
                return Mono.just(ResponseEntity.status(401).body("Token inválido"));
            }
        });
    }
}