package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import java.util.List;

import static ru.practicum.shareit.booking.dto.BookingMapper.toBookingDto;
import static ru.practicum.shareit.booking.dto.BookingMapper.toBookingRequest;


@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingServiceImpl;

    @PostMapping
    public Booking bookingRequest(@RequestBody BookingRequest booking,
                                  @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingServiceImpl.bookingRequest(toBookingRequest(booking), id, booking.getItemId());
    }

    @PatchMapping(value = "{bookingId}")
    public BookingDto confirmOrRejectBooking(@PathVariable Long bookingId,
                                             @RequestParam Boolean approved,
                                             @RequestHeader("X-Sharer-User-Id") Long ownerId) {

        return toBookingDto(bookingServiceImpl.confirmOrRejectBooking(bookingId, approved, ownerId));
    }


    @GetMapping(value = "{bookingId}")
    public BookingDto getBookingByOwner(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return bookingServiceImpl.getBookingByOwner(bookingId, ownerId);
    }

    @GetMapping(value = "/owner")
    public List<BookingDto> getBookingByOwnerAll(@RequestParam(defaultValue = "ALL") String state,
                                                 @RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "99999") Integer size)
    {

        return bookingServiceImpl.getBookingByOwnerAll(ownerId, state, from, size);
    }


    @GetMapping
    public List<BookingDto> getBookingByUserAll(@RequestParam(defaultValue = "ALL") String state,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "99999") Integer size,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {

        return bookingServiceImpl.getBookingByUserAll(state, userId,from,size);
    }


}
