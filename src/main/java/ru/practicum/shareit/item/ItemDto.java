package ru.practicum.shareit.item;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exteption.NullAllowed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class ItemDto {
    private Integer id;
    @NotBlank(groups = NullAllowed.class)
    private String name;
    @NotBlank(groups = NullAllowed.class)
    private String description;
    @NotNull(groups = NullAllowed.class)
    private Boolean available;

}
