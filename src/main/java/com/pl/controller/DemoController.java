package com.pl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class DemoController {

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("hi")
    public ResponseEntity<String>sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
