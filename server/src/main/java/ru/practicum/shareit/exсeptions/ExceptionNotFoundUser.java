package ru.practicum.shareit.exсeptions;

public class ExceptionNotFoundUser extends RuntimeException {
    public ExceptionNotFoundUser(String message) {
        super(message);
    }
}