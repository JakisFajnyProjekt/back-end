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

import java.util.*;

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
        User user = findUser(userId, "Id not found");
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
        User userById = findUser(userId, "Cant delete user with given id");
        userRepository.delete(userById);
        LOGGER.info("User with id " + userId + " deleted");
    }

    private User findUser(long userId, String s) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error(s);
                    throw new NotFoudException("User not found with given id " + userId);
                });
    }



    @Transactional
    public UserDTO editUser(long userId, final Map<String, Object> update) {
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
                    throw new NotFoudException("User Not found");
                });
    }


}
