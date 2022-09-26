package ru.practicum.shareit.requests.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static List<ItemRequestDto> toRequestDto(List<ItemRequest> itemsRequest, List<Item> requestItem) {
        List<ItemRequestDto> itemRequestDto = new ArrayList<>();
        for (ItemRequest itemRequest : itemsRequest) {
            ItemRequestDto request = new ItemRequestDto();
            request.setId(itemRequest.getId());
            request.setDescription(itemRequest.getDescription());
            request.setCreated(itemRequest.getCreated());
            request.setItems(requestItem);
            itemRequestDto.add(request);
        }
        return itemRequestDto;
    }
}
