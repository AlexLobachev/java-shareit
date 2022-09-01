package ru.practicum.shareit.exсeption;

public class ExceptionInvalidEmail extends RuntimeException {
    public ExceptionInvalidEmail(String message) {
        super(message);
    }
}
