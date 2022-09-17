package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.exсeption.NullAllowed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RequestItemDto {
    @NotBlank(groups = NullAllowed.class)
    private String name;
    @NotBlank(groups = NullAllowed.class)
    private String description;
    @NotNull(groups = NullAllowed.class)
    private Boolean available;
    private Long requestId;
}

