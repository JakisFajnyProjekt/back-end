package com.pl.service;

import com.pl.auth.Role;
import com.pl.exception.NotFoundException;
import com.pl.mapper.UserMapper;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.model.dto.UserUpdateDTO;
import com.pl.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
        User entity = findEntity(userRepository, userId);
        if (isAdmin && !loggedUserEmail.equals(entity.getEmail())) {
            LOGGER.info("Administrator deleted user with id " + userId);
            userRepository.delete(entity);
        } else {
            throw new AccessDeniedException("User is not authorized");
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