package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.constants.Constants.FILM_DB_STORAGE;


@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;


    @Autowired
    public FilmService(@Qualifier(FILM_DB_STORAGE) FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public String putLike(Integer id, Integer userId) {
        log.info("FilmService putLike - возрат информации из filmStorage");
        return filmStorage.putLike(id, userId);
    }

    public String deleteLike(Integer id, Integer userId) {
        log.info("FilmService deleteLike - возрат информации из filmStorage");
        return filmStorage.deleteLike(id, userId);
    }

    public List<Film> getTopFilms(Integer count) {
        log.info("FilmService getTopFilms - возрат информации из filmStorage");
        return filmStorage.getTopFilms(count);
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
        return filmStorage.findFilm(id);
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
