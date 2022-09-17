package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.servise.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.practicum.shareit.booking.model.Status.APPROVED;
import static ru.practicum.shareit.booking.model.Status.WAITING;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.shareit"})
class BookingServiceImplTest {
    private final BookingServiceImpl bookingServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Test
    void bookingRequest() {
        userServiceImpl.addUser(new User(3L, "Alex", "Alex@mail.ru"));
        itemServiceImpl.addItem(new Item(1L, "Hammer", "description", true, null, null), 1L);

        Booking booking = bookingServiceImpl.bookingRequest(new Booking(1L, LocalDateTime.now().plusSeconds(50), LocalDateTime.now().plusDays(3), null, null, WAITING), 3L, 1L);
        assertThat(booking.getId(), notNullValue());
    }

    @Test
    void confirmOrRejectBooking() {
        Booking booking = bookingServiceImpl.confirmOrRejectBooking(1L, true, 1L);
        assertThat(booking.getStatus(), equalTo(APPROVED));

    }

    @Test
    void getBookingByOwnerAll() {
        List<BookingDto> bookingDtos = bookingServiceImpl.getBookingByOwnerAll(1L, "ALL", 0, 20);
        BookingDto bookingDto = bookingDtos.get(0);

        assertThat(bookingDto.getId(), notNullValue());
    }

    @Test
    void getBookingByOwner() {
        userServiceImpl.addUser(new User(3L, "Alex", "Alex@mail.ru"));
        itemServiceImpl.addItem(new Item(1L, "Hammer", "description", true, null, null), 1L);
        bookingServiceImpl.bookingRequest(new Booking(1L, LocalDateTime.now().plusSeconds(50), LocalDateTime.now().plusDays(3), null, null, WAITING), 3L, 1L);
        BookingDto bookingDto = bookingServiceImpl.getBookingByOwner(1L, 3L);
        assertThat(bookingDto.getId(), notNullValue());
    }

}