package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exteption.NullAllowed;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.MapperToItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.servise.ItemServiceImpl;
import ru.practicum.shareit.item.storage.InMemoryItemStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.dto.MapperToItemDto.toItemDto;


@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Valid
public class ItemController {
    private InMemoryItemStorage inMemoryItemStorage;
    private ItemServiceImpl itemServiceImpl;

    @PostMapping
    public Item addItem(@Validated(NullAllowed.class)
                        @RequestBody Item item,
                        @RequestHeader("X-Sharer-User-Id") Integer idOwner) {
        return inMemoryItemStorage.addItem(item, idOwner);
    }

    @PatchMapping(value = "{id}")
    public Item updateItem(
            @PathVariable Integer id,
            @RequestBody Item item,
            @RequestHeader("X-Sharer-User-Id") Integer idOwner) {
        return inMemoryItemStorage.updateItem(id, item, idOwner);
    }

    @GetMapping(value = "{id}")
    public ItemDto getItem(@PathVariable Integer id) {
        return toItemDto(inMemoryItemStorage.getItem(id));
    }

    @GetMapping
    public List<ItemDto> getItemsUser(@RequestHeader("X-Sharer-User-Id") Integer idOwner) {
        return itemServiceImpl.getItemsUser(idOwner).stream()
                .map(MapperToItemDto::toItemDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ItemDto> getSearchName(@RequestParam String text) {
        return itemServiceImpl.getSearchName(text).stream()
                .map(MapperToItemDto::toItemDto)
                .collect(Collectors.toList());
    }


}
