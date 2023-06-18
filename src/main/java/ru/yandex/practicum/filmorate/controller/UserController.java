package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.validation.Validation.checkId;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public User find(@PathVariable Integer id) {
        checkId(id);
        log.info("В UserController получен Get запрос (получения информации по пользователю) с id = {}", id);
        return userService.find(id);
    }

    @GetMapping
    public List<User> findAll() {
        log.info("В UserController получен Get запрос (получения списка пользователей)");
        return userService.allUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("В UserController получен Post запрос (создание пользователя) с объектом = {}", user);
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("В UserController получен Put запрос (обновление пользователя) с объектом = {}", user);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        checkId(id);
        log.info("В UserController получен Delete запрос (удаление пользователя) с id = {}", id);
        return userService.deleteUser(id);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        checkId(id, friendId);
        log.info("В UserController получен Put запрос (добавление друга) с id = {} и friendId = {}", id, friendId);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        checkId(id, friendId);
        log.info("В UserController получен Delete запрос (удаление друга) с id = {} и friendId = {}", id, friendId);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> receiveFriends(@PathVariable Integer id) {
        checkId(id);
        log.info("В UserController получен Get запрос (получение списка друзей) с id пользователя = {}", id);
        return userService.receiveFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> receiveCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        checkId(id, otherId);
        log.info("В UserController получен Get запрос (список друзей, общих с другим пользователем) с id = {} и" +
                " otherId = {}", id, otherId);
        return userService.receiveCommonFriends(id, otherId);
    }
}
