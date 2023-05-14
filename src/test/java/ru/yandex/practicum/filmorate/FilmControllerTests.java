package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FilmControllerTests {

    private static final FilmController FILM_CONTROLLER = new FilmController();
    private final Film film = new Film(1, "nisi eiusmod",
            "adipisicing", LocalDate.of(1967, 3, 25), 100);

    @Test
    public void addFilmTest() {
        FILM_CONTROLLER.create(film);
        assertTrue(FILM_CONTROLLER.findAll().contains(film));
    }

    @Test
    public void addFilmTestWithNotCorrectDataTest() {
        final Film wrongFilm = new Film(3, "nisi eiusmod",
                "adipisicing", LocalDate.of(1967, 3, 25), -100);
        Assertions.assertThrows(ValidationException.class, () -> FILM_CONTROLLER.create(wrongFilm));
    }

    @Test
    public void updateFilmTest() throws ValidationException {
        Film filmForUpdate = new Film(1, "nisi eiusmod",
                "adipisicing", LocalDate.of(1967, 3, 25), 87);
        FILM_CONTROLLER.put(filmForUpdate);
        assertTrue(FILM_CONTROLLER.findAll().contains(filmForUpdate));
    }

    @Test
    public void getAllFilmsTest() {
        assertNotNull(FILM_CONTROLLER.findAll());
    }
}
