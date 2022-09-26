package ru.practicum.shareit.exсeptions;

public class ExclusionInvalidRequest extends RuntimeException {
    public ExclusionInvalidRequest(String message) {
        super(message);
    }
}