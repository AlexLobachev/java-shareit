package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.servise.ItemServiceImpl;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemServiceImpl itemServiceImpl;

    @PostMapping
    public ItemDto addItem(
            @RequestBody Item item,
            @RequestHeader("X-Sharer-User-Id") Long idOwner) {
        return itemServiceImpl.addItem(item, idOwner);
    }

    @PostMapping(value = "{id}/comment")
    public Comments addComment(
            @PathVariable Long id,
            @RequestHeader("X-Sharer-User-Id") Long idOwner,
            @RequestBody Comments comments) {
        return itemServiceImpl.addComment(id, idOwner, comments);
    }

    @PatchMapping(value = "{id}")
    public Item updateItem(
            @PathVariable Long id,
            @RequestBody Item item,
            @RequestHeader("X-Sharer-User-Id") Long idOwner) {
        return itemServiceImpl.updateItem(id, item, idOwner);
    }

    @GetMapping(value = "{id}")
    public ItemBookingDto getItem(
            @PathVariable Long id,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemServiceImpl.getItem(id, userId);
    }

    @GetMapping
    public List<ItemBookingDto> getItemsUser(
            @RequestHeader("X-Sharer-User-Id") Long idOwner) {
        return itemServiceImpl.getItemsUser(idOwner);
    }

    @GetMapping("/search")
    public List<ItemDto> getSearchName(
            @RequestParam String text) {
        return itemServiceImpl.getSearchName(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

}
