package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTests {

    @Autowired
    private UserController userController = new UserController();
    private final User user = new User(1, "dolore",
            "mail@mail.ru", " ", LocalDate.of(1946, 8, 20));

    @Test
    public void addUserTest() {
        userController.create(user);
        assertTrue(userController.findAll().contains(user));
    }

    @Test
    public void updateUserTest() throws ValidationException {
        User userForUpdate = new User(1, "doloreUpdate",
                "mail@yandex.ru", "est adipisicing", LocalDate.of(1979, 9, 20));
        userController.update(userForUpdate);
        assertTrue(userController.findAll().contains(userForUpdate));
    }

    @Test
    public void getAllUsersTest() {
        assertNotNull(userController.findAll());
    }

    @Test
    void addUserWithEmptyFields() {
        final User wrongUser = new User(0, "",
                "", "", LocalDate.of(1946, 8, 20));
        Assertions.assertThrows(ValidationException.class, () -> userController.create(wrongUser));
    }

    @Test
    public void addUserTestWithInvalidBirthday() {
        final User wrongUser = new User(2, "dolore",
                "mail@mail.ru", " ", LocalDate.of(2446, 8, 20));
        Assertions.assertThrows(ValidationException.class, () -> userController.create(wrongUser));
    }

    @Test
    void addUserWithInvalidEmail() {
        final User wrongUser = new User(2, "dolore",
                "invalidEmail", " ", LocalDate.of(1946, 8, 20));
        Assertions.assertThrows(ValidationException.class, () -> userController.create(wrongUser));
    }

    @Test
    void addUserWithInvalidLogin() {
        final User wrongUser = new User(2, "",
                "invalidEmail", "estadipisicing", LocalDate.of(1946, 8, 20));
        Assertions.assertThrows(ValidationException.class, () -> userController.create(wrongUser));
    }

    @Test
    void addUserWithInvalidName() {
        final User secondUser = new User(2, "estadipisicing",
                "mail@yandex.rul", "", LocalDate.of(1946, 8, 20));
        userController.create(secondUser);
        assertEquals("User(id=2, login=estadipisicing, email=mail@yandex.rul, name=estadipisicing," +
                " birthday=1946-08-20)", userController.findAll().get(1).toString());
    }
}
