package ru.practicum.shareit.exception;

public class ExceptionInvalidEmail extends RuntimeException {
    public ExceptionInvalidEmail(String message) {
        super(message);
    }
}