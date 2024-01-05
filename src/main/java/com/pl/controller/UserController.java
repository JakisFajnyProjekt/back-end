package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.model.dto.UserUpdateDTO;
import com.pl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}")
    public UserDTO findById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/all")
    public List<UserDTO> list() {
        return userService.list();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> remove(@PathVariable long userId) {
        userService.remove(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> edit(@PathVariable long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.edit(userId, userUpdateDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
