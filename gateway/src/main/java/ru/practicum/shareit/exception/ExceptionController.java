package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice("ru.practicum.shareit")
public class ExceptionController {

    @ExceptionHandler({ValidationException.class, ExclusionInvalidRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> errorException(final RuntimeException e) {

        return Map.of("error", e.getMessage());
    }

}