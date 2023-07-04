package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.constants.Constants.IN_MEMORY_FILM_STORAGE;
import static ru.yandex.practicum.filmorate.validation.Validation.validateFilm;

@Slf4j
@Component
@Qualifier(IN_MEMORY_FILM_STORAGE)
public class InMemoryFilmStorage implements FilmStorage {
    private static int filmId = 1;
    private final Map<Integer, Film> films = new HashMap<>();
    private final InMemoryUserStorage userStorage;

    @Autowired
    public InMemoryFilmStorage(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Film create(Film film) {
        log.info("Получен POST-запрос с объектом Film: {}", film);
        validateFilm(film);
        film.setId(filmId++);
        films.put(film.getId(), film);
        log.info("Фильм {} c id={} был создан, объект: {}", film.getName(), film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film update(Film film) {
        log.info("Получен PUT-запрос с объектом Film: {}", film);
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм " + film.getName() + " c id " + film.getId() + " был обновлен");
            return films.get(film.getId());
        }
        throw new IdNotFoundException("Фильм с id=" + filmId + " не найден");
    }

    @Override
    public String delete(int id) {
        if (films.containsKey(id)) {
            films.remove(id);
            log.info("Фильм c id={} удалён", id);
            return String.format("Фильм c id=%s удалён", id);
        } else {
            throw new IdNotFoundException("Фильм с id=" + id + " не найден");
        }
    }


    @Override
    public Film findFilm(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new IdNotFoundException("Фильм с id=" + id + " не найден");
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public String putLike(Integer id, Integer userId) {
        if (findFilm(id) != null && userStorage.find(userId) != null) {
            findFilm(id).getLikes().add(userId);
            log.info("FilmService putLike - фильму с id = {} поставлен лайк пользователем с userId = {}", id, userId);
            return String.format("Фильму с id=%s поставлен лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    @Override
    public String deleteLike(Integer id, Integer userId) {
        if (findFilm(id) != null && userStorage.find(userId) != null) {
            findFilm(id).getLikes().remove(userId);
            log.info("FilmService deleteLike - у фильма с id = {} удалён лайк пользователем с userId = {}", id, userId);
            return String.format("У фильма с id=%s удалён лайк пользователем с userId=%s.", id, userId);
        }
        throw new IdNotFoundException("Указанные id неверны.");
    }

    @Override
    public List<Film> getTopFilms(Integer count) {
        List<Film> filmList = findAll();
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
}
