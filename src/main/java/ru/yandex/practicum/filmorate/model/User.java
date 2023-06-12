package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data

public class User {
    private int id;
    @NotBlank(message = "Введите логин")
    @NotNull(message = "Введите логин")
    private String login;
    @Email(message = "Введите email")
    @NotBlank(message = "Введите email")
    @NotNull(message = "Введите email")
    private String email;
    private String name;
    @NotNull(message = "Пустая дата рождения")
    @Past(message = "Будущее еще не наступило")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();
}
