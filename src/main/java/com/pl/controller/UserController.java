package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDTO findUserById(@PathVariable long userId){
        return userService.getUserById(userId);
    }

}
