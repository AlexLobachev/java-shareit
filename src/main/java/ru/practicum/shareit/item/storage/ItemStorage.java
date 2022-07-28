package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

public interface ItemStorage {
    Item addItem(Item item, Integer idOwner);

    Item updateItem(Integer id, Item item, Integer idOwner);

    Item getItem(Integer id);
}
