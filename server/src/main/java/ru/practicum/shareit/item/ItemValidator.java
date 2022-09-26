package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exсeption.ExceptionNotFoundUser;
import ru.practicum.shareit.exсeption.ExclusionInvalidRequest;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ItemValidator {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    public void checkUser(Long idOwner, Long id) {
        if (!itemRepository.getById(id).getOwner().getId().equals(idOwner)) {
            throw new ExceptionNotFoundUser("Пользователю не принадлежит эта вещь");
        }
    }
    public void checkItem(Long id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ExceptionNotFoundUser("Вещи не существует");
        }
    }
    public void checkComment(Long id, Long idOwner) {
        if (!bookingRepository.findByItem_id(id, LocalDateTime.now()).contains(idOwner)) {
            throw new ExclusionInvalidRequest("Пользовтаель не брал эту вещь в аренду");
        }
    }
}

