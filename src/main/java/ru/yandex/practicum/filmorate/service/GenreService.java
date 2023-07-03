package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenresDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Component
public class GenreService {
    private final GenresDbStorage genresDbStorage;

    @Autowired
    public GenreService(GenresDbStorage genresDbStorage) {
        this.genresDbStorage = genresDbStorage;
    }

    public List<Genre> getAllGenres() {
        log.info("GenreService getAllGenres - возрат информации из genresDbStorage");
        return genresDbStorage.getAllGenres();
    }

    public Genre getGenre(Integer id) {
        log.info("GenreService getGenre - возрат информации из genresDbStorage");
        return genresDbStorage.getGenre(id);
    }
}