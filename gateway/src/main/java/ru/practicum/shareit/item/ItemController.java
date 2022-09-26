package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NullAllowed;
import ru.practicum.shareit.item.dto.CommentsDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/items")
@AllArgsConstructor
@Valid
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@Validated(NullAllowed.class)
                                          @RequestBody ItemDto itemDto,
                                          @RequestHeader("X-Sharer-User-Id") @NonNull Long idOwner) {
        return itemClient.addItem(itemDto, idOwner);
    }

    @PostMapping(value = "{id}/comment")
    public ResponseEntity<Object> addComment(
            @PathVariable Long id,
            @RequestHeader("X-Sharer-User-Id") Long idOwner,
            @Validated @RequestBody CommentsDto commentsDto) {
        return itemClient.addComment(id, idOwner, commentsDto);
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<Object> updateItem(
            @PathVariable Long id,
            @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long idOwner) {
        return itemClient.updateItem(id, itemDto, idOwner);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemClient.getItem(id, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsUser(@RequestHeader("X-Sharer-User-Id") Long idOwner) {
        return itemClient.getItemsUser(idOwner);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getSearchName(@RequestParam String text) {
        return itemClient.getSearchName(text);
    }
}
