package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validation.Validation.validateUser;


@Slf4j
@Component
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


}
