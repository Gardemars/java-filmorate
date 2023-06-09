package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data

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
    private int duration;
    private Set<Integer> likes = new HashSet<>();
}
