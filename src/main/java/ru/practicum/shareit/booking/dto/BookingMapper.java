package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

import static ru.practicum.shareit.item.dto.ItemMapper.toItemDtoBooking;
import static ru.practicum.shareit.user.dto.UserMapper.toUserDtoBooking;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setBooker(toUserDtoBooking(booking.getBooker()));
        bookingDto.setItem(toItemDtoBooking(booking.getItem()));
        return bookingDto;
    }
    public static Booking toBookingRequest(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setStart(bookingRequest.getStart());
        booking.setEnd(bookingRequest.getEnd());
        return booking;
    }


}
