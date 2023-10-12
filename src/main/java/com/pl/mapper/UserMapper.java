package com.pl.mapper;

import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserMapper {

    public User mapToUser(UserDTO userDto) {
        return new User(
                userDto.name(),
                userDto.email(),
                userDto.password()
        );
    }

    public UserDTO mapToUserDto(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public List<UserDTO> mapToListDto(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
