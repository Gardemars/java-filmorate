package ru.yandex.practicum.filmorate.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.IdNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        log.error("ошибка ValidationException c HttpStatus BAD_REQUEST)");
        log.debug("информация для отладки: {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace());
        return new ErrorResponse("Ошибка в записи параметра.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final IdNotFoundException e) {
        log.error("ошибка IdNotFoundException c HttpStatus NOT_FOUND)");
        log.debug("информация для отладки: {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace());
        return new ErrorResponse("Объект с id не найден.", e.getMessage());
    }
}

