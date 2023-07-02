package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Film {
    private int id;
    @NotBlank(message = "Введите название фильма")
    @NotNull(message = "Введите название фильма")
    private String name;
    @NotBlank(message = "Введите описание фильма")
    @NotNull(message = "Введите описание фильма")
    private String description;
    @NotNull(message = "Пустая дата")
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма должна быть больше 0")
    @NotNull(message = "Длительность фильма должна быть больше 0")
    private long duration;
    private long rate;
    private MpaRating mpa;
    private List<Genre> genres;
    private List<Integer> likes = new ArrayList<>();

    public Film(int id, String name, String description, LocalDate releaseDate, long duration, MpaRating mpa,
                long rate, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
        this.genres = genres;
    }
}
