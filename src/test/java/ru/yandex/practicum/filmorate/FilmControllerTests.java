package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FilmControllerTests {
    @Autowired
    private FilmController filmController;
    private final Film film = new Film(1, "nisi eiusmod",
            "adipisicing", LocalDate.of(1967, 3, 25), 100);

    @Test
    public void addFilmTest() {
        filmController.create(film);
        assertTrue(filmController.findAll().contains(film));
    }

    @Test
    public void updateFilmTest() throws ValidationException {
        Film filmForUpdate = new Film(1, "nisi eiusmod",
                "adipisicing", LocalDate.of(1967, 3, 25), 87);
        filmController.update(filmForUpdate);
        assertTrue(filmController.findAll().contains(filmForUpdate));
    }

    @Test
    public void getAllFilmsTest() {
        assertNotNull(filmController.findAll());
    }

    @Test
    void addFilmUserWithEmptyFields() {
        final Film wrongFilm = new Film(0, "",
                "", LocalDate.of(1967, 3, 25), 100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(wrongFilm));
    }

    @Test
    public void addFilmTestWithNotInvalidDuration() {
        final Film wrongFilm = new Film(3, "nisi eiusmod",
                "adipisicing", LocalDate.of(1967, 3, 25), -100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(wrongFilm));
    }

    @Test
    void addFilmWithInvalidName() {
        final Film wrongFilm = new Film(2, "",
                "adipisicing", LocalDate.of(1967, 3, 25), 100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(wrongFilm));
    }

    @Test
    void addFilmWithInvalidDescription() {
        final Film wrongFilm = new Film(2, "nisi eiusmod",
                "adipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipis" +
                        "icingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicing" +
                        "icingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicing" +
                        "icingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicingadipisicing",
                LocalDate.of(1967, 3, 25), 100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(wrongFilm));
    }

    @Test
    void addFilmWithInvalidReleaseDate() {
        final Film wrongFilm = new Film(3, "nisi eiusmod",
                "adipisicing", LocalDate.of(1840, 3, 25), 100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(wrongFilm));
    }

}
