package com.pl.controller;

import com.pl.security.authentication.AuthenticationRequest;
import com.pl.security.authentication.LoginResponse;
import com.pl.security.authentication.RegisterRequest;
import com.pl.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse register(@Valid @RequestBody RegisterRequest request){
        return authenticationService.register(request);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }


}
