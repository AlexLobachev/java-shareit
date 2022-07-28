package ru.practicum.shareit.exteption;

public class ExclusionInvalidRequest extends RuntimeException {

    public ExclusionInvalidRequest(String message) {
        super(message);
    }
}
