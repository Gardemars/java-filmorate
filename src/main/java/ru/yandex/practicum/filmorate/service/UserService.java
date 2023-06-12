package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserStorage userStorage;

    public String addFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).add(friendId);
            getFriendsId(friendId).add(id);
            return String.format("Пользователь с id=%s добавлен в список друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public String deleteFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).remove(friendId);
            getFriendsId(friendId).remove(id);
            return String.format("Пользователь с id=%s удалён из списка друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public List<User> receiveFriends(Integer id) {
        if (userStorage.find(id) != null) {
            Set<Integer> friendsId = getFriendsId(id);
            return getListOfFriends(friendsId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    public List<User> receiveCommonFriends(Integer id1, Integer id2) {
        if (isUsersNotNull(id1, id2)) {
            Set<Integer> common = new HashSet<>(getFriendsId(id1));
            common.retainAll(getFriendsId(id2));
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
        return listOfFriends;
    }

    public Set<Integer> getFriendsId(Integer id) {
        return userStorage.find(id).getFriends();
    }

    public boolean isUsersNotNull(Integer id, Integer otherId) {
        return userStorage.find(id) != null && userStorage.find(otherId) != null;
    }


    public List<User> allUsers() {
        return userStorage.findAll();
    }

    public User find(int id) {
        return userStorage.find(id);
    }

    public User createUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public String deleteUser(int id) {
        return userStorage.delete(id);
    }
}
