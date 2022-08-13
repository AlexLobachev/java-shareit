package ru.practicum.shareit.exteption;

public class ExceptionInvalidEmail extends RuntimeException {
    public ExceptionInvalidEmail(String message) {
        super(message);
    }
}
