package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validation.Validation.validateFilm;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static int filmId = 1;
    private final Map<Integer, Film> films = new HashMap<>();


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
    public Film find(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new IdNotFoundException("Фильм с id=" + id + " не найден");
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

}
