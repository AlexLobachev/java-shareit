package ru.practicum.shareit.requests.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class ItemRequestDto {
    private Long id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private List<Item> items = new ArrayList<>();
}
