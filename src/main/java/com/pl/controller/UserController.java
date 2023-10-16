package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{userId}")
    public UserDTO findUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("")
    public List<UserDTO> listUsers(){
        return userService.listUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void>removeUser(@PathVariable long userId){
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO>editUser(@PathVariable long userId,@RequestBody UserDTO user){
        userService.editUser(userId, user);



}
