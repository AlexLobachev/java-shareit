package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.RequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {
    private final RequestsClient requestsClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@Validated @RequestBody RequestDto requestDto,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestsClient.addRequest(requestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestsClient.getRequest(userId);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllRequest(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "9999") Integer size,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {

        return requestsClient.getAllRequest(from, size, userId);
    }

    @GetMapping(value = "{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestsClient.getRequestById(requestId, userId);
    }

}
