package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

import static ru.practicum.shareit.booking.dto.BookingMapper.toBookingDto;


@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingServiceImpl;
    @PostMapping
    public Booking bookingRequest (@RequestBody Booking booking,
                                   @RequestHeader("X-Sharer-User-Id") Long id){
        return bookingServiceImpl.bookingRequest(booking, id);
    }
    @PatchMapping(value = "{bookingId}")
    public BookingDto confirmOrRejectBooking(@PathVariable Long bookingId,
                                             @RequestParam Boolean approved,
                                             @RequestHeader("X-Sharer-User-Id") Long ownerId){

        return toBookingDto(bookingServiceImpl.confirmOrRejectBooking(bookingId, approved, ownerId));
    }


    @GetMapping(value = "{bookingId}")
    public BookingDto getBookingByOwner(@PathVariable Long bookingId, @RequestHeader ("X-Sharer-User-Id") Long ownerId){
        return bookingServiceImpl.getBookingByOwner(bookingId,ownerId);
    }
    @GetMapping(value = "/owner")
    public List<BookingDto> getBookingByOwnerAll(@RequestParam (defaultValue = "ALL") String state,
                                                 @RequestHeader ("X-Sharer-User-Id") Long ownerId){

       return bookingServiceImpl.getBookingByOwnerAll(ownerId,state);
    }


    @GetMapping
    public List<BookingDto> getBookingByUserAll(@RequestParam (defaultValue = "ALL") String state,
                                            @RequestHeader("X-Sharer-User-Id") Long userId)  {
        return bookingServiceImpl.getBookingByUserAll(state, userId);
    }




}
