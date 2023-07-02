package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MpaRating {
    @NonNull
    private int id;
    private String name;

    public MpaRating() {

    }
}