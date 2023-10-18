package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.exception.UserEmailTakenException;
import com.pl.model.User;
import com.pl.repository.UserRepository;
import com.pl.security.JwtService;
import com.pl.security.Role;
import com.pl.security.authentication.AuthenticationRequest;
import com.pl.security.authentication.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class UserAuthenticationServiceTest {

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void cleanUp() {
        Mockito.reset(userRepository, jwtService, passwordEncoder);
    }


    @Test
    void shouldRegisterUser() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setPassword("password");
        request.setEmail("email@email.com");

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // When
        var registerUser = userAuthenticationService.register(request);

        // Then
        assertThat(registerUser).isNotNull();
        assertThat(registerUser.getToken()).isEqualTo("jwtToken");
    }
    @Test
    void shouldThrowExceptionWhenEmailIsTaken() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setPassword("password");
        request.setEmail("email@email.com");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User()));

        //When & Then
        assertThatThrownBy(() -> userAuthenticationService.register(request))
                .isInstanceOf(UserEmailTakenException.class);
    }
    @Test
    void shouldAuthenticateUser() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("email@email.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        )).thenReturn(null);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // When
        var authenticationResponse = userAuthenticationService.authenticate(request);

        // Then
        assertThat(authenticationResponse).isNotNull();
        assertThat(authenticationResponse.getToken()).isEqualTo("jwtToken");
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("email@email.com");
        request.setPassword("password");

        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        )).thenReturn(null);

        Mockito.when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> userAuthenticationService.authenticate(request))
                .isInstanceOf(NotFoundException.class);
    }
}
