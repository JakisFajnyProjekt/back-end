package com.pl.controller;

import com.pl.security.authentication.AuthenticationRequest;
import com.pl.security.authentication.AuthenticationResponse;
import com.pl.security.authentication.RegisterRequest;
import com.pl.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request) {
        return userAuthenticationService.register(request);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userAuthenticationService.authenticate(request));
    }


}
