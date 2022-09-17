package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.servise.RequestServiceImpl;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final RequestServiceImpl requestService;

    @PostMapping
    public ItemRequest addRequest(@RequestBody ItemRequest request,
                                  @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.addRequest(request, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.getRequest(userId);
    }

    @GetMapping(value = "/all")
    public List<ItemRequestDto> getAllRequest(@RequestParam(required = false) Integer from,
                                              @RequestParam(required = false) Integer size,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.getAllRequest(from, size, userId);
    }

    @GetMapping(value = "{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.getRequestById(requestId, userId);
    }

}
