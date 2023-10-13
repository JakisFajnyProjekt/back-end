package com.pl.mapper;

import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserMapper {

    public User mapToUser(UserDTO userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.password(),
                userDto.role()
        );
    }

    public UserDTO mapToUserDto(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public List<UserDTO> mapToListDto(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
