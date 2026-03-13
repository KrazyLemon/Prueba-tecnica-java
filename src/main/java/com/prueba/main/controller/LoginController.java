package com.prueba.main.controller;


import com.prueba.main.model.AuthResponse;
import com.prueba.main.model.LoginRequest;
import com.prueba.main.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class LoginController{
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws Exception {
        Optional<AuthResponse> response = authService.login(request);
        if(response.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(response.get());
    }
}