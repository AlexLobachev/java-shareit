package ru.practicum.shareit.requests.servise;

import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface RequestService {
    ItemRequest addRequest(ItemRequest request, Long userId);

    List<ItemRequestDto> getRequest(Long userId);

    List<ItemRequestDto> getAllRequest(Integer from, Integer size, Long userId);

    ItemRequestDto getRequestById(Long requestId, Long userId);
}

