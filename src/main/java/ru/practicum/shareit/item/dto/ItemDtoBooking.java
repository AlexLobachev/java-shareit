package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.exсeption.NullAllowed;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
public class ItemDtoBooking {
    @NotBlank(groups = NullAllowed.class)
    private Long id;
    @NotBlank(groups = NullAllowed.class)
    private String name;
}
