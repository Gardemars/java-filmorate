package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.validation.Validation.checkId;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> findAll() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film find(@PathVariable Integer id) {
        checkId(id);
        return filmService.findFilm(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        checkId(id);
        return filmService.deleteFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public String putLike(@PathVariable Integer id, @PathVariable Integer userId) {
        checkId(id, userId);
        return filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public String deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        checkId(id, userId);
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") Integer count) {
        if (count < 1) {
            throw new ValidationException("Введите положительное число 'count'.");
        }
        return filmService.getTopFilms(count);
    }
}
