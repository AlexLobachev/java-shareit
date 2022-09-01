package ru.practicum.shareit.item.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.exсeption.NullAllowed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank(groups = NullAllowed.class)
    private String name;
    @NotBlank(groups = NullAllowed.class)
    private String description;
    @NotNull(groups = NullAllowed.class)
    private Boolean available;

}
