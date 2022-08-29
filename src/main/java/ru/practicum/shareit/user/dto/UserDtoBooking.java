package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.exсeption.NullAllowed;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDtoBooking {
    @NotBlank(groups = NullAllowed.class)
    private Long id;
}
