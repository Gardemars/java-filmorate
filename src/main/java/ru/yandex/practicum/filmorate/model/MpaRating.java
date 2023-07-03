package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MpaRating {
    @NonNull
    private int id;
    private String name;
}