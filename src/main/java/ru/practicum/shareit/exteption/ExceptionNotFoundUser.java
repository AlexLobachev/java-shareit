package ru.practicum.shareit.exteption;

public class ExceptionNotFoundUser extends RuntimeException {
    public ExceptionNotFoundUser(String message) {
        super(message);
    }
}
