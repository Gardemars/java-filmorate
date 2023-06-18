package ru.yandex.practicum.filmorate.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class ErrorResponse {
    public String error;
    public String description;
}