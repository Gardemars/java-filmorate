package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

import static ru.yandex.practicum.filmorate.constants.Constants.IN_MEMORY_USER_STORAGE;
import static ru.yandex.practicum.filmorate.validation.Validation.validateUser;

@Slf4j
@Component
@Qualifier(IN_MEMORY_USER_STORAGE)
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private static int userId = 1;

    @Override
    public User create(User user) {
        log.info("Получен POST-запрос с объектом User: {}", user);
        validateUser(user);
        user.setId(userId++);
        users.put(user.getId(), user);
        log.info("Пользователь {} c id={} добавлен", user.getName(), user.getId());
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        log.info("Получен PUT-запрос с объектом User: {}", user);
        validateUser(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь " + user.getName() + " c id " + user.getId() + " был обновлен");
            return users.get(user.getId());
        }
        throw new IdNotFoundException("Фильм с id=" + user.getId() + " не найден");
    }

    @Override
    public String delete(int id) {
        if (users.containsKey(id)) {
            users.remove(id);
            log.info("Пользователь c id={} удалён", id);
            return String.format("Пользователь c id=%s удалён", id);
        } else {
            throw new IdNotFoundException("Фильм с id=" + id + " не найден");
        }
    }

    @Override
    public User find(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new IdNotFoundException("Пользователь с id=" + id + " не найден");
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public String addFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).add(friendId);
            getFriendsId(friendId).add(id);
            log.info("UserService addFriend - пользователь с id = {} добавлен в список друзей.", friendId);
            return String.format("Пользователь с id=%s добавлен в список друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    @Override
    public String deleteFriend(Integer id, Integer friendId) {
        if (isUsersNotNull(id, friendId)) {
            getFriendsId(id).remove(friendId);
            getFriendsId(friendId).remove(id);
            log.info("UserService deleteFriend - пользователь с id = {} удалён из списка друзей.", friendId);
            return String.format("Пользователь с id=%s удалён из списка друзей.", friendId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    @Override
    public List<User> receiveFriends(Integer id) {
        if (find(id) != null) {
            Set<Integer> friendsId = getFriendsId(id);
            log.info("UserService receiveFriends - возрат информации из userStorage по друзьям пользователя с id = {}",
                    id);
            return getListOfFriends(friendsId);
        }
        throw new IdNotFoundException("Пользователь с указанным id не найден");
    }

    @Override
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
                for (User user : findAll()) {
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
        return find(id).getFriends();
    }

    @Override
    public boolean isUsersNotNull(Integer id, Integer otherId) {
        log.info("UserService isUsersNotNull- проверка id = {} и id = {} на null", id, otherId);
        return find(id) != null && find(otherId) != null;
    }
}
