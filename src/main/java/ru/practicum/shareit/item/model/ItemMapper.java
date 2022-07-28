package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.ItemDto;

public interface ItemMapper {
    ItemDto toItemDto(Item item);
}
