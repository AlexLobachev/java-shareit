package ru.practicum.shareit.requests.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.requests.OffsetLimitPageable;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static ru.practicum.shareit.requests.dto.RequestMapper.toRequestDto;

@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {

    private final UserServiceImpl userService;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;


    public ItemRequest addRequest(ItemRequest request, Long userId) {
        request.setRequestor(userService.getUser(userId));
        return itemRequestRepository.save(request);
    }

    public List<ItemRequestDto> getRequest(Long userId) {
        userService.getUser(userId);
        return toRequestDto(itemRequestRepository.findAllByRequestorId(userId), itemRepository.findAllByRequestId(userId));//!!!Переделать по человечески

    }

    public List<ItemRequestDto> getAllRequest(Integer from, Integer size, Long userId) {
        userService.getUser(userId);
        Pageable pageable = OffsetLimitPageable.of(from, size);
        return toRequestDto(itemRequestRepository.findAllByRequestorIdNot(userId, pageable), itemRepository.findAllByRequestIdNotNull());
    }

    public ItemRequestDto getRequestById(Long requestId, Long userId) {
        userService.getUser(userId);
        userService.getUser(requestId);
        return toRequestDto(itemRequestRepository.findAllById(requestId), itemRepository.findAllByRequestId(requestId)).get(0);
    }
}
