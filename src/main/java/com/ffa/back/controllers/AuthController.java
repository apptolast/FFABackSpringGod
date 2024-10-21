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
        return null;
        //return authService.login(userRequest);
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@AuthenticationPrincipal Mono<Authentication> authenticationMono) {
        return null;
        //return authService.register(new UserRequestRegisterDTO());
    }
}
