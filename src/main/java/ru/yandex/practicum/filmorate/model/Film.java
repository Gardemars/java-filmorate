package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank (message = "Введите название фильма")
    @NotNull (message = "Введите название фильма")
    private String name;
    @NotBlank (message = "Введите описание фильма")
    @NotNull (message = "Введите описание фильма")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма должна быть больше 0")
    @NotNull(message = "Длительность фильма должна быть больше 0")
    private int duration;
}
