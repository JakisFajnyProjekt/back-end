package com.pl.service;

import com.pl.exception.NotFoudException;
import com.pl.mapper.UserMapper;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoudException("User not found with given id " + userId));
        LOGGER.info("User founded with id" + userId);
        return userMapper.mapToUserDto(user);
    }

    public List<UserDTO> getListOfAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            LOGGER.info("the users list are empty");
            return new ArrayList<>();
        }
        return userMapper.mapToListDto(allUsers);
    }

    @Transactional
    public void deleteUserFromDb(long userId) {
        User userById = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoudException("User not found with given id " + userId));
        userRepository.delete(userById);
        LOGGER.info("User with id " + userId + " deleted");
    }

    @Transactional
    public UserDTO editUser(long userId, final Map<String, Object> update) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    if (update.containsKey("firstName")) {
                        existingUser.setFirstName(update.get("firstName").toString());
                    }
                    if (update.containsKey("lastName")) {
                        existingUser.setLastName(update.get("lastName").toString());
                    }
                    if (update.containsKey("email")) {
                        existingUser.setEmail(update.get("email").toString());
                    }
                    User savedUser = userRepository.save(existingUser);
                    LOGGER.info("Changes are accepted");
                    return userMapper.mapToUserDto(savedUser);
                }).orElseThrow(() -> {
                    LOGGER.error("Wrong user id");
                    throw new NotFoudException("User Not found");
                });
    }


}
