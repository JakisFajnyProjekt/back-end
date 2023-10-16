package com.pl.controller;

import com.pl.model.dto.UserDTO;
import com.pl.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public UserDTO findUserById(@PathVariable long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/all")
    public List<UserDTO> findAllUsersFromDB(){
        return userService.getListOfAllUsers();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void>deleteUserFromDb(@PathVariable long userId){
        userService.deleteUserFromDb(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<UserDTO>updateUser(@PathVariable long userId,@RequestBody UserDTO userDTO){
        userService.editUser(userId,userDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
