package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.exteption.NullAllowed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {
    private Integer id;
    @NotBlank(groups = NullAllowed.class)
    private String name;
    @NotBlank(groups = NullAllowed.class)
    @Email(groups = NullAllowed.class)
    private String email;
}
