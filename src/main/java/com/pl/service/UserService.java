package com.pl.service;

import com.pl.exception.AuthenticationError;
import com.pl.exception.AuthenticationErrorException;
import com.pl.exception.NotFoundException;
import com.pl.mapper.UserMapper;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.model.dto.UserUpdateDTO;
import com.pl.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractService<UserRepository, User> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUserById(long userId) {
        LOGGER.info("User found with id" + userId);
        return userMapper.mapToUserDto(findEntity(userRepository, userId));
    }

    public List<UserDTO> list() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            LOGGER.info("the list of users are empty");
            return new ArrayList<>();
        }
        return userMapper.mapToListDto(users);
    }

    @Transactional
    public void remove(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            User userFromDB = findEntity(userRepository, userId);

            if (userFromDB != null && name.equals(userFromDB.getEmail())) {
                userRepository.delete(userFromDB);
                LOGGER.info("User with id " + userId + " deleted");
            } else {
                LOGGER.warn("Unauthorized attempt to delete user with id " + userId);
                throw new AuthenticationErrorException(AuthenticationError.AUTHENTICATION_ERROR,"Unauthorized attempt");
            }
        } else {
            LOGGER.warn("Unauthorized attempt to access delete user functionality");
        }
    }

    @Transactional
    public UserDTO edit(long userId, final UserUpdateDTO update) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    Optional.ofNullable(update.firstName())
                            .ifPresent(existingUser::setFirstName);
                    Optional.ofNullable(update.lastName())
                            .ifPresent(existingUser::setLastName);
                    Optional.ofNullable(update.email())
                            .ifPresent(existingUser::setEmail);
                    Optional.ofNullable(update.password())
                            .map(passwordEncoder::encode)
                            .ifPresent(existingUser::setPassword);
                    User savedUser = userRepository.save(existingUser);
                    LOGGER.info("Changes are accepted");
                    return userMapper.mapToUserDto(savedUser);
                })
                .orElseThrow(() -> {
                    LOGGER.error("Wrong user id");
                    return new NotFoundException("User Not found");
                });
    }

}