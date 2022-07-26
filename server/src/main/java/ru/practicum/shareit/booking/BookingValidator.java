package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.ExceptionNotFoundUser;
import ru.practicum.shareit.exception.ExclusionInvalidRequest;
import ru.practicum.shareit.item.servise.ItemServiceImpl;

import static ru.practicum.shareit.booking.model.Status.APPROVED;
import static ru.practicum.shareit.booking.model.Status.REJECTED;

@AllArgsConstructor
@Service
public class BookingValidator {
    private ItemServiceImpl itemServiceImpl;

    public void checkPost(Booking booking, Long itemId) {

        if (!itemServiceImpl.getItem(itemId, booking.getBooker().getId()).getAvailable()) {
            throw new ExclusionInvalidRequest("Вещь недоступна для бронирования");
        }
        if (itemServiceImpl.getItem(itemId, booking.getBooker().getId()).getOwner().getId().equals(booking.getBooker().getId())) {
            throw new ExceptionNotFoundUser("Пользователь не может бронировать свои вещи");
        }
    }

    public BookingDto checkGetBookerAndUser(BookingDto bookingDto, Long ownerId) {
        if (bookingDto.getId() != null && (bookingDto.getBooker().getId().equals(ownerId)) ||
                (itemServiceImpl.getItem(bookingDto.getItem().getId(), bookingDto.getBooker().getId()).getOwner().getId().equals(ownerId))) {
            return bookingDto;
        }
        throw new ExceptionNotFoundUser("Только владелец или автор бронирования могут запрашивать вещь!");
    }

    public Status decryptsTheStatus(Boolean status) {

        if (status) {
            return APPROVED;
        }
        return REJECTED;
    }

    public void checkIdOwnerAndStatus(Booking booking, Long ownerId) {
        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new ExceptionNotFoundUser("Только владелец может подтвердить бронирование");
        }
        if (booking.getStatus() == APPROVED) {
            throw new ExclusionInvalidRequest("Статус уже установлен");
        }
    }

}



