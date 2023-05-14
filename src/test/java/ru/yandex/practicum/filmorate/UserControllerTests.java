package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerTests {

    private static final UserController USER_CONTROLLER = new UserController();
    private final User user = new User(1, "dolore",
            "mail@mail.ru", " ", LocalDate.of(1946, 8, 20));

    @Test
    public void addUserTest() {
        USER_CONTROLLER.create(user);
        assertTrue(USER_CONTROLLER.findAll().contains(user));
    }

    @Test
    public void addUserTestWithNotCorrectDataTest() {
        final User wrongUser = new User(2, "dolore",
                "mail@mail.ru", " ", LocalDate.of(2446, 8, 20));
        Assertions.assertThrows(ValidationException.class, () -> USER_CONTROLLER.create(wrongUser));
    }

    @Test
    public void updateUserTest() throws ValidationException {
        User userForUpdate = new User(1, "doloreUpdate",
                "mail@yandex.ru", "est adipisicing", LocalDate.of(1979, 9, 20));
        USER_CONTROLLER.put(userForUpdate);
        assertTrue(USER_CONTROLLER.findAll().contains(userForUpdate));
    }

    @Test
    public void getAllFilmsTest() {
        assertNotNull(USER_CONTROLLER.findAll());
    }
}
