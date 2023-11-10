package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.mapper.UserMapper;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.model.dto.UserUpdateDTO;
import com.pl.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        userRepository.delete(findEntity(userRepository, userId));
        LOGGER.info("User with id " + userId + " deleted");
    }

    @Transactional
    public UserDTO edit(long userId, final UserUpdateDTO update) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    if (update.firstName() != null) {
                        existingUser.setFirstName(update.firstName());
                    }
                    if (update.lastName() != null) {
                        existingUser.setLastName(update.lastName());
                    }
                    if (update.email() != null) {
                        existingUser.setEmail(update.email());
                    }
                    if (update.password() != null) {
                        existingUser.setPassword(passwordEncoder.encode(update.password()));
                    }
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