package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.validation.Validation.checkId;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User find(@PathVariable Integer id) {
        checkId(id);
        return userService.find(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.allUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        checkId(id);
        return userService.deleteUser(id);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        checkId(id, friendId);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        checkId(id, friendId);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> receiveFriends(@PathVariable Integer id) {
        checkId(id);
        return userService.receiveFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> receiveCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        checkId(id, otherId);
        return userService.receiveCommonFriends(id, otherId);
    }
}
