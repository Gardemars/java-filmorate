package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> findAll();

    User create(User user);

    User update(User user);

    String delete(int id);

    User find(int id);

    String addFriend(Integer id, Integer friendId);

    String deleteFriend(Integer id, Integer friendId);

    List<User> receiveFriends(Integer id);

    List<User> receiveCommonFriends(Integer id, Integer otherId);

    boolean isUsersNotNull(Integer id, Integer otherId);
}
