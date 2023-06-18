package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public String putLike(Integer id, Integer userId) {
        if (filmStorage.find(id) != null && userStorage.find(userId) != null) {
            filmStorage.find(id).getLikes().add(userId);
            log.info("FilmService putLike - фильму с id = {} поставлен лайк пользователем с userId = {}", id, userId);
            return String.format("Фильму с id=%s поставлен лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    public String deleteLike(Integer id, Integer userId) {
        if (filmStorage.find(id) != null && userStorage.find(userId) != null) {
            filmStorage.find(id).getLikes().remove(userId);
            log.info("FilmService deleteLike - у фильма с id = {} удалён лайк пользователем с userId = {}", id, userId);
            return String.format("У фильма с id=%s удалён лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    public List<Film> getTopFilms(Integer count) {
        List<Film> filmList = filmStorage.findAll();
        log.info("FilmService getTopFilms- возрат информации из filmStorage по получению списка попупулярных фильмов" +
                " с количеством лайков = {}", count);
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
        log.info("FilmService getFilms - возрат информации из filmStorage о нахождении фильмов");
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        log.info("FilmService create - возрат информации из filmStorage о создании фильма = {}", film);
        return filmStorage.create(film);
    }

    public Film findFilm(int id) {
        log.info("FilmService findFilm - возратинформации из filmStorage о поиске фильма с id = {}", id);
        return filmStorage.find(id);
    }

    public Film updateFilm(Film film) {
        log.info("FilmService updateFilm - возрат информации из filmStorage об обновлении фильма = {}", film);
        return filmStorage.update(film);
    }

    public String deleteFilm(int id) {
        log.info("FilmService deleteFilm - возрат информации из filmStorage об удалении фильма с id = {}", id);
        return filmStorage.delete(id);
    }
}
