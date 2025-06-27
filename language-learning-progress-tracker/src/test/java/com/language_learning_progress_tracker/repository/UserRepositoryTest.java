package com.language_learning_progress_tracker.repository;

import com.language_learning_progress_tracker.entity.Language;
import com.language_learning_progress_tracker.entity.User;
import com.language_learning_progress_tracker.entity.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_Save_ReturnSavedUser() {

        //Arrange
        User user = new User("username", "password", UserRole.USER);

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() >= 1L);
    }

    @Test
    void UserRepository_GetAllReturnMoreThanOne() {
        User user1 = new User("username", "password", UserRole.USER);
        User user2 = new User("username2", "password", UserRole.USER);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> allUsers = userRepository.findAll();

        Assertions.assertNotNull(allUsers);
        Assertions.assertTrue(allUsers.size() > 1);

    }

    @Test
    void UserRepository_FindById_ReturnsUser() {
        User user = new User("username", "password", UserRole.USER);

        userRepository.save(user);

        User userById = userRepository.findById(user.getId()).get();

        Assertions.assertNotNull(userById);
    }

    @Test
    void UserRepository_FindByUsername_ReturnUserNotNull() {
        User user = new User("username", "password", UserRole.USER);

        userRepository.save(user);

        User userByUsername = userRepository.findByUsername(user.getUsername()).get();

        Assertions.assertNotNull(userByUsername);

    }

    @Test
    void UserRepository_UpdateUser_ReturnUserNotNull() {
        User user = new User("username", "password", UserRole.USER);

        userRepository.save(user);

        User userById = userRepository.findById(user.getId()).get();
        userById.setUsername("Jason");

        User updatedUser = userRepository.save(userById);

        Assertions.assertNotNull(updatedUser.getUsername());

    }

    @Test
    void UserRepository_DeleteById_ReturnUserIsEmpty() {
        User user = new User("username", "password", UserRole.USER);

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> byId = userRepository.findById(user.getId());

        Assertions.assertTrue(byId.isEmpty());

    }
}