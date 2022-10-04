package ru.practicum.shareit.item.servise;

import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.model.Comments;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item, Long idOwner);

    Item updateItem(Long id, Item item, Long idOwner);

    ItemBookingDto getItem(Long id, Long userId);

    List<ItemBookingDto> getItemsUser(Long idOwner);

    List<Item> getSearchName(String text);

    Comments addComment(Long id, Long idOwner, Comments comments);
}
