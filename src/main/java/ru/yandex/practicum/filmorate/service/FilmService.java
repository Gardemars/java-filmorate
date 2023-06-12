package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilmService {
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;


    public String putLike(Integer id, Integer userId) {
        if (filmStorage.find(id) != null && userStorage.find(userId) != null) {
            filmStorage.find(id).getLikes().add(userId);
            return String.format("Фильму с id=%s поставлен лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    public String deleteLike(Integer id, Integer userId) {
        if (filmStorage.find(id) != null && userStorage.find(userId) != null) {
            filmStorage.find(id).getLikes().remove(userId);
            return String.format("У фильма с id=%s удалён лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    public List<Film> getTopFilms(Integer count) {
        List<Film> filmList = filmStorage.findAll();
        return filmList.stream().sorted((po, p1) -> {
            if (po.getLikes().size() > p1.getLikes().size()) {
                return -1;
            } else if (po.getLikes().size() < p1.getLikes().size()) {
                return 1;
            }
            return 0;
        }).limit(count).collect(Collectors.toList());
    }

    public List<Film> getFilms() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film findFilm(int id) {
        return filmStorage.find(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public String deleteFilm(int id) {
        return filmStorage.delete(id);
    }
}
