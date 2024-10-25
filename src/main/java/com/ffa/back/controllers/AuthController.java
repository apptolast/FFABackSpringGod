package com.ffa.back.controllers;

import com.ffa.back.services.AuthService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("familyfilmapp/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@AuthenticationPrincipal Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Mono.just(ResponseEntity.status(401).body("No autorizado"));
        }

        String uid = authentication.getName();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        return authService.register(uid, email);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@AuthenticationPrincipal Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Mono.just(ResponseEntity.status(401).body("No autorizado"));
        }

        String uid = authentication.getName();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        return authService.login(uid, email);
    }
}