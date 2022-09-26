package ru.practicum.shareit.exception;

public class ExceptionNotFoundUser extends RuntimeException {
    public ExceptionNotFoundUser(String message) {
        super(message);
    }
}