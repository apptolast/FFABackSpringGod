package com.ffa.back.controllers;

import com.ffa.back.dto.UserRequestRegisterDTO;
import com.ffa.back.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("familyfilmapp/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin
    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@AuthenticationPrincipal Mono<Authentication> authenticationMono) {
        return authenticationMono.flatMap(authentication -> {
            // Verificar si la autenticación es válida
            if (authentication == null || !authentication.isAuthenticated()) {
                return Mono.just(ResponseEntity.status(401).body("Unauthorized"));
            }

            // Extraer información del usuario
            String uid = authentication.getName();
            String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

            // Llamar al servicio de autenticación
            return authService.login(uid, email);
        });
    }

    @CrossOrigin
    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(@AuthenticationPrincipal Mono<Authentication> authenticationMono) {
        return authenticationMono.flatMap(authentication -> {
            // Verificar si la autenticación es válida
            if (authentication == null || !authentication.isAuthenticated()) {
                return Mono.just(ResponseEntity.status(401).body("Unauthorized"));
            }

            // Extraer información del usuario
            String uid = authentication.getName();
            String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

            // Llamar al servicio de registro
            return authService.register(uid, email);
        });

    }
}
