package com.pl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.model.User;
import com.pl.auth.Role;
import com.pl.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private

    @Test
    void shouldFindAllUsers(){
        //Given
        List<User> userList=List.of(new User("firstname","lastname","password","email@email.com", Role.USER),
                new User("firstname","lastname","password","email@email.com", Role.USER),
                new User("firstname","lastname","password","email@email.com", Role.USER));


    }


}
