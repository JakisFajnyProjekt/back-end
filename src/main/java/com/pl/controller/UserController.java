package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/{userId}")
    public UserDTO findUserById(@PathVariable long userId, @RequestHeader("Authorization") String auth) {
        return userService.getUserById(userId);
    }

    @GetMapping()
    public List<UserDTO> listUsers() {
        return userService.listUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeUser(@PathVariable long userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> editUser(@PathVariable long userId, @RequestBody Map<String, Object> user) {
        userService.editUser(userId, user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
