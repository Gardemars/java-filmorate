package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private static int userId = 1;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (validationUser(user)) {
            user.setId(userId++);
            users.put(user.getId(), user);
            log.info("Пользователь " + user.getName() + " c id " + user.getLogin() + " добавлен");
        }
        return users.get(user.getId());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (validationUser(user) && users.containsKey(user.getId())) {

            users.put(user.getId(), user);
            log.info("Пользователь " + user.getName() + " c id " + user.getId() + " был обновлен");
            return users.get(user.getId());
        } else {
            log.error("ошибка в обновлении пользователя " + user);
            throw new ValidationException("Пользователь с id " + user.getId() + " отсутсвует");
        }
    }

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    private boolean validationUser(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@") || user.getLogin().isBlank() ||
                user.getLogin().contains(" ") || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Ошибка в полях пользователя " + user);
            throw new ValidationException("Ошибка в полях пользователя");
        } else {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
                log.info("Так как отсутствовало имя пользователя вместо него был использован логин " + user.getLogin());
            }
            return true;
        }
    }
}
