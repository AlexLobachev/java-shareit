package ru.practicum.shareit.exсeptions;

public class ExceptionInvalidEmail extends RuntimeException {
    public ExceptionInvalidEmail(String message) {
        super(message);
    }
}