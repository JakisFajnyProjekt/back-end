package com.pl.mapper;

import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @InjectMocks
    UserMapper userMapper;

    User user;
    UserDTO userDto;
    UserDTO expectedDto;

    @BeforeEach
    void testData(){
        user = new User("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER);
        userDto = new UserDTO("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER);
        expectedDto = new UserDTO("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER);
    }
    @Test
    void shouldMapToDto() {
        //Given
        //When
        UserDTO attemptUserDto = userMapper.mapToUserDto(user);
        //Then
        assertEquals(expectedDto.name(), attemptUserDto.name());
        assertEquals(expectedDto.password(), attemptUserDto.password());
        assertEquals(expectedDto.email(), attemptUserDto.email());
        assertEquals(expectedDto.role(), attemptUserDto.role());
    }
    @Test
    void shouldMapFromDto() {
        //Given
        //When
        User attemptUser = userMapper.mapToUser(userDto);
        //Then
        assertEquals(expectedDto.name(), attemptUser.getName());
        assertEquals(expectedDto.password(), attemptUser.getPassword());
        assertEquals(expectedDto.email(), attemptUser.getEmail());
        assertEquals(expectedDto.role(), attemptUser.getRole());
    }
    @Test
    void shouldMapToListDto() {
        //Given
        List<User> users = List.of(
                new User("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER),
                new User("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER),
                new User("Bartosz", "bartosz@gmail.com", "zaq1@WSX", Role.USER)
        );
        //When
        List<UserDTO> attemptList = userMapper.mapToListDto(users);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(UserDTO.class, attemptList.get(0).getClass());
    }
}
