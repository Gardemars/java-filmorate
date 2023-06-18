package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public String addFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).add(friendId);
            getFriendsId(friendId).add(id);
            log.info("UserService addFriend - пользователь с id = {} добавлен в список друзей.", friendId);
            return String.format("Пользователь с id=%s добавлен в список друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public String deleteFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).remove(friendId);
            getFriendsId(friendId).remove(id);
            log.info("UserService deleteFriend - пользователь с id = {} удалён из списка друзей.", friendId);
            return String.format("Пользователь с id=%s удалён из списка друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public List<User> receiveFriends(Integer id) {
        if (userStorage.find(id) != null) {
            Set<Integer> friendsId = getFriendsId(id);
            log.info("UserService receiveFriends - возрат информации из userStorage по друзьям пользователя с id = {}",
                    id);
            return getListOfFriends(friendsId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public List<User> receiveCommonFriends(Integer id1, Integer id2) {
        if (isUsersNotNull(id1, id2)) {
            Set<Integer> common = new HashSet<>(getFriendsId(id1));
            common.retainAll(getFriendsId(id2));
            log.info("UserService receiveCommonFriends - возрат информации из userStorage по общим друзьям " +
                            "пользователя с id = {} и пользователя с id = {}", id1, id2);
            return getListOfFriends(common);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }


    public List<User> getListOfFriends(Set<Integer> friendsId) {
        List<User> listOfFriends = new ArrayList<>();

        if (friendsId != null && !friendsId.isEmpty()) {
            for (Integer i : friendsId) {
                for (User user : userStorage.findAll()) {
                    if (user.getId() == i) {
                        listOfFriends.add(user);
                    }
                }
            }
        }
        log.info("UserService getListOfFriends - возрат информации из userStorage списка друзей с id = {}", friendsId);
        return listOfFriends;
    }

    public Set<Integer> getFriendsId(Integer id) {
        log.info("UserService getFriendsId - возрат информации из userStorage списка друзей с id = {}", id);
        return userStorage.find(id).getFriends();
    }

    public boolean isUsersNotNull(Integer id, Integer otherId) {
        log.info("UserService isUsersNotNull- проверка id = {} и id = {} на null", id, otherId);
        return userStorage.find(id) != null && userStorage.find(otherId) != null;
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
