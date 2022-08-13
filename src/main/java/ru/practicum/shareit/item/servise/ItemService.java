package ru.practicum.shareit.item.servise;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    List<Item> getItemsUser(Integer idOwner);

    Collection<Item> getSearchName(String text);
}
