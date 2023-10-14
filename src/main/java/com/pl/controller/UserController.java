package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get/{userId}")
    public UserDTO findUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping(value = "mofidy/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO modifyUser(@RequestBody UserDTO userDTO, @PathVariable long userId) {
        return userService.editUser(userId, userDTO);
    }

}
