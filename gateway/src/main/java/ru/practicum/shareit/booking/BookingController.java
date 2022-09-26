package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> bookingRequest(@Validated @RequestBody BookingDto bookingDto,
                                                 @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingClient.bookingRequest(bookingDto, id, bookingDto.getItemId());
    }

    @PatchMapping(value = "{bookingId}")
    public ResponseEntity<Object> confirmOrRejectBooking(@PathVariable Long bookingId,
                                                         @RequestParam Boolean approved,
                                                         @RequestHeader("X-Sharer-User-Id") Long ownerId) throws UnsupportedEncodingException {


        return bookingClient.confirmOrRejectBooking(bookingId, approved, ownerId);
    }


    @GetMapping(value = "{bookingId}")
    public ResponseEntity<Object> getBookingByOwner(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long ownerId) {

        return bookingClient.getBookingByOwner(bookingId, ownerId);
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<Object> getBookingByOwnerAll(@RequestParam(defaultValue = "ALL") String state,
                                                       @RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive @RequestParam(defaultValue = "99999") Integer size) {
        BookingState stateParam = BookingState.from(state)
                .orElseThrow(() -> new ValidationException("Unknown state: " + state));
        return bookingClient.getBookingByOwnerAll(stateParam, ownerId, from, size);
    }


    @GetMapping
    public ResponseEntity<Object> getBookingByUserAll(@RequestParam(defaultValue = "ALL") String state,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @PositiveOrZero @RequestParam(defaultValue = "99999") Integer size,
                                                      @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        BookingState stateParam = BookingState.from(state)
                .orElseThrow(() -> new ValidationException("Unknown state: " + state));
        return bookingClient.getBookingByUserAll(stateParam, userId, from, size);

    }

}
