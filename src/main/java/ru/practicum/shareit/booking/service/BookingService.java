package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking bookingRequest(Booking booking, Long id);

    Booking confirmOrRejectBooking(Long bookingId, Boolean status, Long ownerId);

    BookingDto getBookingByOwner(Long id, Long ownerId);

    List<BookingDto> getBookingByOwnerAll(Long ownerId, String state);

    List<BookingDto> getBookingByUserAll(String state, Long userId);
}
