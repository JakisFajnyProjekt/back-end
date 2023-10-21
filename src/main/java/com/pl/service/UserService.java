package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.mapper.UserMapper;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends AbstractService<UserRepository, User> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
    public UserDTO edit(long userId, final Map<String, Object> update) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    Optional.ofNullable(update.get("firstName"))
                            .ifPresent(value -> existingUser.setFirstName(value.toString()));
                    Optional.ofNullable(update.get("lastName"))
                            .ifPresent(value -> existingUser.setLastName(value.toString()));
                    Optional.ofNullable(update.get("email"))
                            .ifPresent(value -> existingUser.setEmail(value.toString()));
                    User savedUser = userRepository.save(existingUser);
                    LOGGER.info("Changes are accepted");
                    return userMapper.mapToUserDto(savedUser);
                }).orElseThrow(() -> {
                    LOGGER.error("Wrong user id");
                    return new NotFoundException("User Not found");
                });
    }

}