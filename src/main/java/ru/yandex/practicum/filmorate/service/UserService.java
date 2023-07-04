package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.constants.Constants.USER_DB_STORAGE;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier(USER_DB_STORAGE) UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public String addFriend(Integer id, Integer friendId) {
        log.info("UserService addFriend - возрат информации из userStorage");
        return userStorage.addFriend(id, friendId);
    }

    public String deleteFriend(Integer id, Integer friendId) {
        log.info("UserService deleteFriend - возрат информации из userStorage");
        return userStorage.deleteFriend(id, friendId);
    }

    public List<User> receiveFriends(Integer id) {
        log.info("UserService receiveFriends - возрат информации из userStorage");
        return userStorage.receiveFriends(id);
    }

    public List<User> receiveCommonFriends(Integer id1, Integer id2) {
        log.info("UserService receiveCommonFriends - возрат информации из userStorage");
        return userStorage.receiveCommonFriends(id1, id2);
    }

    public List<User> allUsers() {
        log.info("UserService allUsers - возрат информации из userStorage  по пользователям");
        return userStorage.findAll();
    }

    public User find(int id) {
        log.info("UserService find - возрат информации из userStorage по нахождению пользователя c id = {}", id);
        return userStorage.find(id);
    }

    public User createUser(User user) {
        log.info("UserService createUser - возрат информации из userStorage по созданию пользователя = {}", user);
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        log.info("UserService updateUser- возрат информации из userStorage по обновлению пользователя = {}", user);
        return userStorage.update(user);
    }

    public String deleteUser(int id) {
        log.info("UserService deleteUser - возрат информации из userStorage по удалению пользователя с id = {}", id);
        return userStorage.delete(id);
    }
}
