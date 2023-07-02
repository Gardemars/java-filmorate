package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> findAll();

    Film create(Film film);

    Film update(Film film);

    String delete(int id);

    Film findFilm(int id);

    String putLike(Integer id, Integer userId);

    String deleteLike(Integer id, Integer userId);

    List<Film> getTopFilms(Integer count);
}
