package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.GenresDbStorage;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = "classpath:data.sql", executionPhase = AFTER_TEST_METHOD)
public class IntegrationTest {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;
    private final GenresDbStorage genresDbStorage;
    private final MpaDbStorage mpaDbStorage;

    private final static LocalDate DATE = LocalDate.of(1995, 5, 20);
    User firstUser = new User(0, "test1@mail.ru", "test1", null, DATE);
    User secondUser = new User(0, "test2@mail.ru", "test2", null, DATE);
    User thirdUser = new User(0, "test3@mail.ru", "test3", null, DATE);

    Film firstFilm = new Film(0, "film1", "...", DATE, 100, new MpaRating(1), 10,
            List.of(new Genre(1, null), new Genre(4, null)));
    Film secondFilm = new Film(0, "film2", "...", DATE, 100, new MpaRating(3), 100,
            List.of(new Genre(2, null), new Genre(6, null)));
    Film thirdFilm = new Film(0, "film3", "...", DATE, 100, new MpaRating(3), 100,
            List.of(new Genre(2, null), new Genre(6, null)));
    Film update = new Film(1, "update_film1", "...", DATE, 100, new MpaRating(3),
            100, List.of(new Genre(2), new Genre(6)));

    @Test
    public void createUserTest() {
        userStorage.create(firstUser);
        Optional<User> userOptional = Optional.of(userStorage.find(1));
        userOptional.ifPresent(user -> checkUser(user, "test1@mail.ru", "test1",
                "test1"));
    }

    @Test
    public void getUsersTest() {
        userStorage.create(firstUser);
        assertEquals(1, userStorage.findAll().size());
        userStorage.create(secondUser);
        assertEquals(2, userStorage.findAll().size());
    }


    @Test
    public void findUserByIdTest() {
        userStorage.create(new User(0, "test@mail.ru", "test", null, DATE));
        userStorage.create(secondUser);
        Optional<User> userOptional = Optional.of(userStorage.find(1));
        userOptional.ifPresent(user -> assertEquals(1, user.getId()));
    }

    @Test
    public void findUserByWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.find(9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void updateUserTest() {
        userStorage.create(new User(0, "test@mail.ru", "test", null, DATE));
        userStorage.update(new User(1, "update_test@mail.ru", "update_test", null,
                DATE));
        Optional<User> userOptional = Optional.of(userStorage.find(1));
        userOptional.ifPresent(user -> checkUser(user, "update_test@mail.ru",
                "update_test", "update_test"));
    }

    @Test
    public void updateUserWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.update(new User(9999, "update_test@mail.ru", "update_test",
                        null, DATE)));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void deleteUserTest() {
        userStorage.create(firstUser);
        assertEquals(1, userStorage.findAll().size());
        userStorage.create(secondUser);
        assertEquals(2, userStorage.findAll().size());
        userStorage.delete(1);
        assertEquals(1, userStorage.findAll().size());

        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.find(1));

        assertEquals("Пользователь с id=1 не найден", exception.getMessage());
    }

    @Test
    public void deleteUserWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.delete(9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void addFriendTest() {
        addFriendPattern();
    }

    @Test
    public void addFriendWithWrongIdTest() {
        userStorage.create(firstUser);

        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.addFriend(1, 9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void deleteFriendTest() {
        addFriendPattern();

        assertEquals("Пользователь с id=2 удалён из списка друзей.", userStorage.deleteFriend(1, 2));
    }

    @Test
    public void deleteFriendWithWrongIdTest() {
        addFriendPattern();

        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.deleteFriend(1, 9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void receiveFriendsTest() {
        addFriendPattern();

        userStorage.create(thirdUser);
        userStorage.addFriend(1, 3);

        assertEquals(2, userStorage.receiveFriends(1).size());
        assertEquals(0, userStorage.receiveFriends(2).size());
        assertEquals(0, userStorage.receiveFriends(3).size());
    }

    @Test
    public void receiveFriendsWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.receiveFriends(9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void receiveCommonFriendsTest() {
        userStorage.create(firstUser);
        userStorage.create(secondUser);
        userStorage.addFriend(1, 2);
        userStorage.addFriend(2, 1);

        userStorage.create(thirdUser);
        userStorage.addFriend(1, 3);
        userStorage.addFriend(3, 1);

        assertEquals(1, userStorage.receiveCommonFriends(2, 3).get(0).getId());
    }

    @Test
    public void receiveCommonFriendsWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> userStorage.receiveCommonFriends(2, 9999));

        assertEquals("Пользователь с id=2 не найден", exception.getMessage());
    }


    @Test
    public void createFilmTest() {
        filmStorage.create(firstFilm);
        Optional<Film> filmOptional = Optional.of(filmStorage.findFilm(1));
        filmOptional.ifPresent(film -> checkFilm(film, "film1",
                new MpaRating(1, "G")));
    }

    @Test
    public void findAllFilmsTest() {
        filmStorage.create(firstFilm);
        assertEquals(1, filmStorage.findAll().size());
        filmStorage.create(secondFilm);
        assertEquals(2, filmStorage.findAll().size());
    }


    @Test
    public void findFilmByIdTest() {
        filmStorage.create(firstFilm);
        Optional<Film> filmOptional = Optional.of(filmStorage.findFilm(1));
        filmOptional.ifPresent(film -> assertEquals(1, film.getId()));
    }

    @Test
    public void findFilmByWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.findFilm(9999));

        assertEquals("Фильм с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void updateFilmTest() {
        filmStorage.create(firstFilm);
        filmStorage.update(update);
        Optional<Film> filmOptional = Optional.of(filmStorage.findFilm(1));
        filmOptional.ifPresent(film -> checkFilm(film, "update_film1",
                new MpaRating(3, "PG-13")));
    }

    @Test
    public void updateWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.update(update));

        assertEquals("Фильм с id=1 не найден", exception.getMessage());
    }


    @Test
    public void deleteFilmTest() {
        filmStorage.create(firstFilm);
        assertEquals(1, filmStorage.findAll().size());
        filmStorage.create(secondFilm);
        assertEquals(2, filmStorage.findAll().size());
        filmStorage.delete(1);
        assertEquals(1, filmStorage.findAll().size());

        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.findFilm(1));

        assertEquals("Фильм с id=1 не найден", exception.getMessage());
    }

    @Test
    public void deleteFilmWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.delete(9999));

        assertEquals("Фильм с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void putLikeTest() {
        putLikePattern();
    }

    @Test
    public void putLikeWithWrongIdTest() {
        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.putLike(1, 9999));

        assertEquals("Фильм с id=1 не найден", exception.getMessage());
    }

    @Test
    public void deleteLikeTest() {
        putLikePattern();

        assertEquals("У фильма с id=1 удалён лайк пользователем с userId=1.", filmStorage.deleteLike(1,
                1));
    }

    @Test
    public void deleteLikeWithWrongIdTest() {
        putLikePattern();

        final IdNotFoundException exception = assertThrows(
                IdNotFoundException.class,
                () -> filmStorage.deleteLike(1, 9999));

        assertEquals("Пользователь с id=9999 не найден", exception.getMessage());
    }

    @Test
    public void getTopListTest() {
        filmStorage.create(firstFilm);
        filmStorage.create(secondFilm);
        filmStorage.create(thirdFilm);
        filmStorage.getUserDbStorage().create(firstUser);
        filmStorage.getUserDbStorage().create(secondUser);

        filmStorage.putLike(1, 1);
        filmStorage.putLike(1, 2);
        filmStorage.putLike(2, 2);

        List<Film> topList = filmStorage.getTopFilms(10);

        assertEquals(1, topList.get(0).getId());
        assertEquals(2, topList.get(1).getId());
        assertEquals(3, topList.get(2).getId());

        topList = filmStorage.getTopFilms(2);
        assertEquals(2, topList.size());
    }


    @Test
    public void getAllGenresTest() {
        assertEquals(6, genresDbStorage.getAllGenres().size());
    }

    @Test
    public void getGenreTest() {
        assertEquals("Драма", genresDbStorage.getGenre(2).getName());
        assertEquals("Документальный", genresDbStorage.getGenre(5).getName());
    }

    @Test
    public void getAllMpaTest() {
        assertEquals(5, mpaDbStorage.getAllMpa().size());
    }

    @Test
    public void getMpaTest() {
        assertEquals("PG", mpaDbStorage.getMpa(2).getName());
        assertEquals("NC-17", mpaDbStorage.getMpa(5).getName());
    }

    private void checkUser(User user, String expEmail, String expLogin, String expName) {
        assertEquals(1, user.getId());
        assertEquals(expEmail, user.getEmail());
        assertEquals(expLogin, user.getLogin());
        assertEquals(expName, user.getName());
        assertEquals(DATE, user.getBirthday());
    }

    private void checkFilm(Film film, String expName, MpaRating expMpa) {
        assertEquals(1, film.getId());
        assertEquals(expName, film.getName());
        assertEquals("...", film.getDescription());
        assertEquals(100, film.getDuration());
        assertEquals(expMpa.getId(), film.getMpa().getId());
        assertEquals(10, film.getRate());
        assertEquals(2, film.getGenres().size());
        assertEquals(DATE, film.getReleaseDate());
    }

    private void addFriendPattern() {
        userStorage.create(firstUser);
        userStorage.create(secondUser);

        assertEquals("Пользователь с id=2 добавлен в список друзей.", userStorage.addFriend(1, 2));
    }

    private void putLikePattern() {
        filmStorage.getUserDbStorage().create(firstUser);
        filmStorage.create(firstFilm);

        assertEquals("Фильму с id=1 поставлен лайк пользователем с userId=1.", filmStorage.putLike(1,
                1));
    }

}