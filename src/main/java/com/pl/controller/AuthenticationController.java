package com.pl.controller;

import com.pl.auth.authentication.LoginRequest;
import com.pl.auth.authentication.LoginResponse;
import com.pl.auth.authentication.RegisterRequest;
import com.pl.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
