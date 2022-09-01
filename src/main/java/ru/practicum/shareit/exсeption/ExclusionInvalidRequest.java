package ru.practicum.shareit.exсeption;

public class ExclusionInvalidRequest extends RuntimeException {

    public ExclusionInvalidRequest(String message) {
        super(message);
    }
}
