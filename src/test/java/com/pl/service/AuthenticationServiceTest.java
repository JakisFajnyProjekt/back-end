package com.pl.service;

import com.pl.config.MessagePropertiesConfig;
import com.pl.exception.AuthenticationErrorException;
import com.pl.exception.NotFoundException;
import com.pl.exception.UserEmailTakenException;
import com.pl.model.User;
import com.pl.repository.UserRepository;
import com.pl.auth.JwtService;
import com.pl.auth.Role;
import com.pl.auth.authentication.LoginRequest;
import com.pl.auth.authentication.LoginResponse;
import com.pl.auth.authentication.RegisterRequest;
import com.pl.token.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private MessagePropertiesConfig message;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
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
        var registerUser = authenticationService.register(request);

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
        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(UserEmailTakenException.class);
    }
    @Test
    void shouldAuthenticateUser() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // When
        LoginResponse loginResponse = authenticationService.login(request);

        // Then
        assertEquals("jwtToken", loginResponse.getToken());
    }

    @Test
    void shouldHandleExceptionWhenUserIsNotFound() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setEmail("email@gmail.com");
        request.setPassword("Qwe123");

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        )).thenReturn(null);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> authenticationService.login(request))
                .isInstanceOf(AuthenticationErrorException.class);
    }

    @Test
    void shouldHandleExceptionWhenUserPasswordIsWrong(){
        //Given
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("testPassword123");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        //When && Then
        String exceptedMessage = message.getInvalidPassword();
        AuthenticationErrorException notFoundException = assertThrows(AuthenticationErrorException.class,
                () -> authenticationService.login(request));
        assertEquals(exceptedMessage,notFoundException.getMessage());
        assertThatThrownBy(()-> authenticationService.login(request))
                .isInstanceOf(AuthenticationErrorException.class);


    }
}