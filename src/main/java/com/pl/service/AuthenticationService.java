package com.pl.service;

import com.pl.exception.AuthenticationError;
import com.pl.exception.AuthenticationErrorException;
import com.pl.exception.UserEmailTakenException;
import com.pl.model.User;
import com.pl.repository.UserRepository;
import com.pl.auth.JwtService;
import com.pl.auth.Role;
import com.pl.auth.authentication.LoginRequest;
import com.pl.auth.authentication.LoginResponse;
import com.pl.auth.authentication.RegisterRequest;
import com.pl.token.Token;
import com.pl.token.TokenRepository;
import com.pl.token.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        emailCheck(request.getEmail());
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        var savedUser = userRepository.save(user);
        LOGGER.info("User successfully created with id " + user.getId());
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void removeUserTokenIfExists(User user) {
        Optional<Token> tokenToDelete = tokenRepository.findByUser(user);
        if (tokenToDelete.isPresent()) {
            Token deletedToken = tokenToDelete.get();
            tokenRepository.delete(deletedToken);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    public LoginResponse login(LoginRequest request){
        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AuthenticationErrorException(AuthenticationError.EMAIL, "Email not found"));
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
                );
                removeUserTokenIfExists(user);
                var jwtToken = jwtService.generateToken(user);
                saveUserToken(user, jwtToken);
                return LoginResponse.builder()
                        .token(jwtToken)
                        .build();
            } else {
                throw new AuthenticationErrorException(AuthenticationError.PASSWORD, "Password not found");
            }
        } catch (AuthenticationErrorException ex) {
            throw new RuntimeException(ex.getMessage());

        }

    }

    public void emailCheck(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            throw new UserEmailTakenException("Email already taken");
        }
    }

}
