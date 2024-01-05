package com.pl.service;

import com.pl.auth.Role;
import com.pl.exception.NotFoundException;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.model.dto.UserUpdateDTO;
import com.pl.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser(roles = "ADMIN")

public class UserServiceTest {
    User user2;
    User user3;
    User user4;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private User user1;
    private UserDTO update;
    private UserUpdateDTO userUpdateDTO;
    private UserUpdateDTO userUpdateDTOWithNull;
    private List<User> userList;

    @BeforeEach
    void testData() {
        user1 = new User("firstNameUser",
                "lastNameUser", "email@gmail.com", "123456789qwErty_user1", Role.USER);

        userList = List.of(
                user2 = new User("firstNameUser",
                        "lastNameUser", "email_user2@gmail.com", "123456789Qwerty_user2", Role.ADMIN),
                user3 = new User("firstNameUser",
                        "lastNameUser", "email_user3@gmail.com", "123456789Qwerty_user3", Role.USER),
                user4 = new User("firstNameUser",
                        "lastNameUser", "email_user4@gmail.com", "123456789Qwerty_user4", Role.USER)
        );

        update = new UserDTO("firstNameDto",
                "lastNameDto", "email@gmail.com", "123456789Qwerty", Role.USER);
        userUpdateDTO = new UserUpdateDTO("firstNameDto",
                "lastNameDto", "email@gmail.com", "123456789Qwerty");
        userUpdateDTOWithNull = new UserUpdateDTO(null, null, "newEmail@gmail.com", null);
    }

    @AfterEach
    void cleanUpAfter() {
        userRepository.deleteAll();
    }

    @BeforeEach
    void cleanUpaBefore() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindUserByGivenId() {
        //Given --> testData
        User saveUser = userRepository.save(user1);
        //When
        UserDTO userById = userService.getUserById(saveUser.getId());
        //Then
        assertEquals("firstNameUser", userById.firstName());
        assertEquals("lastNameUser", userById.lastName());
    }

    @Test
    void shouldHandleExceptionUserNotFoundTryingRetriveUserById() {
        //Given --> testData
        long nonExistingUserId = 100L;
        //When
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class,
                () -> userService.getUserById(nonExistingUserId)
        );
        //Then
        String expectedMessage = "Not found with given id " + nonExistingUserId;
        String actualMessage = notFoundException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldFindAllUsersFromDb() {
        //Given
        userRepository.saveAll(userList);
        //When
        List<UserDTO> listOfAllUsers = userService.list();
        //Then
        int expectedSizeOfList = 3;
        assertEquals(expectedSizeOfList, listOfAllUsers.size());
    }

    @Test
    void shouldReturnEmptyListIfNoUsersInDb() {
        //Given
        int expectedSize = 0;
        //When
        List<UserDTO> listOfAllUsers = userService.list();
        //Then
        assertEquals(expectedSize, listOfAllUsers.size());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void shouldDeleteUserFromDb() {
        //Given
        List<User> userSavingList = userRepository.saveAll(userList);
        Long idOfUserForDelete = userSavingList.get(1).getId();
        //When
        int expectedSizeBeforeDelete = 3;
        int expectedSizeAfterDelete = 2;
        int sizeBeforeDeletingUser = userRepository.findAll().size();
        userService.remove(idOfUserForDelete);
        int sizeAfterDeletingUser = userRepository.findAll().size();
        //Then
        assertEquals(expectedSizeBeforeDelete, sizeBeforeDeletingUser);
        assertEquals(expectedSizeAfterDelete, sizeAfterDeletingUser);
    }

    @Test
    void shouldHandleNotFoundExceptionWhileTryingToDeleteByWrongId() {
        //Given
        long nonExistingUserId = 100L;
        //When
        NotFoundException userNotFound = assertThrows(
                NotFoundException.class,
                () -> userService.remove(nonExistingUserId)
        );
        String expectedMessage = "Not found with given id " + nonExistingUserId; //need to change message
        String messageFromException = userNotFound.getMessage();
        //Then
        assertTrue(messageFromException.contains(expectedMessage));
    }

    @Test
    void shouldModifyExistingUser() {
        // Given
        User savedUser = userRepository.save(user1);
        Long idOfUserInDb = savedUser.getId();
        // When
        UserDTO modifyUser = userService.edit(idOfUserInDb, userUpdateDTO);
        // Then
        assertEquals("firstNameDto", modifyUser.firstName());
        assertEquals("lastNameDto", modifyUser.lastName());
    }

    @Test
    void shouldHandleExceptionWhileTryingToFindUserForUpdate() {
        //Given
        long nonExistingId = 12;
        //When
        String expectedMessage = "User Not found";
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.edit(nonExistingId, userUpdateDTO));
        String notFoundExceptionMessage = notFoundException.getMessage();
        //Then
        assertTrue(notFoundExceptionMessage.contains(expectedMessage));
    }

    @Test
    void shouldAcceptOnlyNonNullValuesWhileUpdatingUser() {
        //Given
        User savedUser = userRepository.save(user1);
        Long savedUserId = savedUser.getId();
        //When
        UserDTO updateWithNulls = userService.edit(savedUserId, userUpdateDTOWithNull);
        //Then
        assertEquals("firstNameUser", updateWithNulls.firstName());
        assertEquals("lastNameUser", updateWithNulls.lastName());
        assertEquals("newEmail@gmail.com", updateWithNulls.email());
    }
}
