package com.pl.service;

import com.pl.exception.NotFoundException;
import com.pl.model.User;
import com.pl.model.dto.UserDTO;
import com.pl.repository.UserRepository;
import com.pl.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user1;
    private UserDTO update;
    private UserDTO updateWithNull;
    private List<User> userList;

    @BeforeEach
    void testData() {
        user1 = new User("firstName_user1",
                "lastName_user1", "email_user1", "123456789qwerty_user1", Role.USER);

        User user2;
        User user3;
        User user4;
        userList = List.of(
                user2 = new User("firstName_user2",
                        "lastName_user2", "email_user2", "123456789qwerty_user2", Role.USER),
                user3 = new User("firstName_user3",
                        "lastName_user3", "email_user3", "123456789qwerty_user3", Role.USER),
                user4 = new User("firstName_user4",
                        "lastName_user4", "email_user4", "123456789qwerty_user4", Role.USER)
        );

        update = new UserDTO("firstName_dto",
                "lastName_dto", "email", "123456789qwerty", Role.USER);
    }

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindUserByGivenId() {
        //Given --> testData
        User saveUser = userRepository.save(user1);

        //When
        UserDTO userById = userService.getUserById(saveUser.getId());

        //Then
        assertEquals("firstName_user1", userById.firstName());
        assertEquals("lastName_user1", userById.lastName());
    }

    @Test
    void shouldHandleExceptionUserNotFoundTryingRetriveUserById() {
        //Given --> testData
        long nonExistingUserId = 1;

        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.getUserById(nonExistingUserId));

        //Then
        String expectedMessage = "Order not found with given id "+ nonExistingUserId;
        String actualMessage = notFoundException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shoudlFindAllUsersFromDb() {
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
    void shoudlHandleNotFoundExceptionWhileTryingToDeleteByWrongId() {
        //Given
        long nonexistingUserId = 100;

        //Whne
        NotFoundException userNotFound = assertThrows(NotFoundException.class,
                ()->userService.remove(nonexistingUserId));
        String expectedMessage = "Order not found with given id " + nonexistingUserId; //need to change message

        String meesageFromException = userNotFound.getMessage();

        assertTrue(meesageFromException.contains(expectedMessage));
    }

    @Test
    void shouldModifyExistingUser() {
        // Given
        User savedUser = userRepository.save(user1);
        Long idOfUserInDb = savedUser.getId();

        // When
        UserDTO modifyUser = userService.edit(idOfUserInDb, update);

        // Then
        assertEquals("firstName_dto", modifyUser.firstName());
        assertEquals("lastName_dto", modifyUser.lastName());

    }

    @Test
    void shouldHandleExceptionWhileTryingToFindUserForUpdate() {
        //Given
        long nonExistingId = 12;

        //When
        String expectedMessage = "User Not found";
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.edit(nonExistingId, update));
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
        UserDTO updateWithNulls = userService.edit(savedUserId, updateWithNull);

        //Then
        assertEquals("firstName_user1", updateWithNulls.firstName());
        assertEquals("lastName_user1", updateWithNulls.lastName());
    }


}
