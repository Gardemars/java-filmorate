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

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        log.info("В FilmControler получен Get запрос (получение списка фильмов)");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film find(@PathVariable Integer id) {
        checkId(id);
        log.info("В FilmControler получен Get запрос (получение фильма по id) с id = {}", id);
        return filmService.findFilm(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("В FilmControler получен Post запрос (cоздание нового фильма) с объектом Film = {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("В FilmControler получен Put запрос (обновление фильма) с объектом Film = {}", film);
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        checkId(id);
        log.info("В FilmControler получен Delete запрос (удаление фильма) с id = {}", id);
        return filmService.deleteFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public String putLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("В FilmControler получен Put запрос (добавление лайка) с id =  {} и userId = {}", id, userId);
        checkId(id, userId);
        return filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public String deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("В FilmControler получен Delete запрос (удаление лайка) с id = {} и userId = {}", id, userId);
        checkId(id, userId);
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") Integer count) {
        if (count < 1) {
            throw new ValidationException("Введите положительное число 'count'.");
        }
        log.info("В FilmControler получен Get запрос (получение списка попупулярных фильмов) с количеством лайков = {}", count);
        return filmService.getTopFilms(count);
    }
}
