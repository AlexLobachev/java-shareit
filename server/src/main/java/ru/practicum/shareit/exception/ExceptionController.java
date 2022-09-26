package ru.practicum.shareit.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice("ru.practicum.shareit")
public class ExceptionController {

    @ExceptionHandler({ValidationException.class, ExclusionInvalidRequest.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> errorException(final RuntimeException e) {

        return Map.of("error", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> errorException(final ExceptionNotFoundUser e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> errorException(final ExceptionInvalidEmail e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> errorException(final HttpMessageNotWritableException e) {
        return Map.of("ERROR", "ОШИБКА ВВОДА",
                "errorMessage", "Пользователь не существует");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> errorException(final DataIntegrityViolationException e) {
        return Map.of("error", Objects.requireNonNull(e.getMessage()));
    }
}