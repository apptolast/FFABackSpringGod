package com.ffa.back.controllers;

import com.ffa.back.models.User;
import com.ffa.back.models.UserTokenReponse;
import com.ffa.back.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("familyfilmapp/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<UserTokenReponse> login(@RequestBody User user) {
        return authService.login(user);
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }
}
