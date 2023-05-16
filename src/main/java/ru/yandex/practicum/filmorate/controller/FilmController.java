package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private static final LocalDate START_DATE = LocalDate.of(1895, 12, 28);
    private static int filmId = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (validationFilm(film)) {
            film.setId(filmId++);
            films.put(film.getId(), film);
            log.info("Фильм " + film.getName() + " c id " + film.getId() + " был создан");
        }
        return films.get(film.getId());
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (validationFilm(film) && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм " + film.getName() + " c id " + film.getId() + " был обновлен");
            return films.get(film.getId());
        } else {
            log.error("ошибка в обновлении фильма" + film);
            throw new ValidationException("Фильм с id " + film.getId() + " отсутсвует");
        }
    }

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    private boolean validationFilm(Film film) {
        if (film.getName().isEmpty() || film.getDescription().getBytes().length > 200 ||
                film.getReleaseDate().isBefore(START_DATE) || film.getDuration() < 1) {
            log.error("ошибка в полях фильма" + film);
            throw new ValidationException("Ошибка в полях фильма");
        } else {
            return true;
        }
    }
}
