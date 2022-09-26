package ru.practicum.shareit.exсeption;

public class ExceptionNotFoundUser extends RuntimeException {
    public ExceptionNotFoundUser(String message) {
        super(message);
    }
}
