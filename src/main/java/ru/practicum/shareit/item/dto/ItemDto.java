package ru.practicum.shareit.item.dto;


import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.exteption.NullAllowed;
import ru.practicum.shareit.requests.model.ItemRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ItemDto {
    private Integer id;
    @NotBlank(groups = NullAllowed.class)
    private String name;
    @NotBlank(groups = NullAllowed.class)
    private String description;
    @NotNull(groups = NullAllowed.class)
    private Boolean available;
    private ItemRequest request;

}
