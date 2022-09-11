package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exсeption.ExceptionNotFoundUser;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.OffsetLimitPageable;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.dto.BookingMapper.toBookingDto;
import static ru.practicum.shareit.booking.model.Status.*;

public interface BookingService {
    Booking bookingRequest(Booking booking, Long id, Long itemId);


    Booking confirmOrRejectBooking(Long bookingId, Boolean status, Long ownerId);


    BookingDto getBookingByOwner(Long id, Long ownerId);

    List<BookingDto> getBookingByOwnerAll(Long ownerId, String state, Integer from, Integer size);

    List<BookingDto> getBookingByUserAll(String state, Long userId, Integer from, Integer size);
}
