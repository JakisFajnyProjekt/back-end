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
    public UserDTO editUser(long userId, final UserDTO userDTO) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    User user = userMapper.mapToUser(userDTO);
                    user.setId(userId);
                    user.setLastName(userDTO.lastName());
                    user.setFirstName(userDTO.firstName());
                    user.setPassword(existingUser.getPassword());
                    user.setEmail(existingUser.getEmail());
                    user.setRole(existingUser.getRole());
                    User saveUser = userRepository.save(user);
                    LOGGER.info("Changes are accepted");
                    return userMapper.mapToUserDto(saveUser);
                }).orElseThrow(() -> {
                    LOGGER.error("Wrong user id");
                    throw new NotFoudException("User Not found");
                });
    }


}
