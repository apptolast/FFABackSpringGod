package com.ffa.back.controllers;

import com.ffa.back.dto.UserRequestRegister;
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
    public ResponseEntity<?> login(@RequestBody UserRequestRegister userRequest) {
        return authService.login(userRequest);
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestRegister userRequest) {
        return authService.register(userRequest);
    }
}
