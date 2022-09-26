package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.servise.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.shareit"})
class BookingServiceImplTest {
    private final BookingServiceImpl bookingServiceImpl;
    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private Booking booking;
    private  User user;
    private  User user2;
    private  Item item;
    @BeforeEach
    void beforeEach(){
        user = new User();
        user.setName("Alex");
        user.setEmail("Alex@mail.ru");

        user2 = new User();
        user2.setName("Oleg");
        user2.setEmail("Oleg@mail.ru");

        item = new Item();
        item.setName("Hammer");
        item.setDescription("description");
        item.setAvailable(true);

        booking = new Booking();
        booking.setStart(LocalDateTime.now().plusSeconds(50));
        booking.setEnd(LocalDateTime.now().plusDays(3));
        booking.setItem(item);

    }
    @Test
    void bookingRequest() {
        userServiceImpl.addUser(user);
        userServiceImpl.addUser(user2);
        itemServiceImpl.addItem(item, user.getId());

        booking = bookingServiceImpl.bookingRequest(booking, user2.getId(), item.getId());
        assertThat(booking.getId(), notNullValue());

        bookingServiceImpl.deleteAll();
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
        userServiceImpl.deleteUser(user2.getId());

    }

    @Test
    void confirmOrRejectBooking() {
        userServiceImpl.addUser(user);
        userServiceImpl.addUser(user2);
        itemServiceImpl.addItem(item, user.getId());
        bookingServiceImpl.bookingRequest(booking, user2.getId(), item.getId());

        Booking booking2 = bookingServiceImpl.confirmOrRejectBooking(booking.getId(), true,     user.getId());
        assertThat(booking2.getStatus(), equalTo(Status.APPROVED));

        bookingServiceImpl.deleteAll();
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
        userServiceImpl.deleteUser(user2.getId());
    }

    @Test
    void getBookingByOwnerAll() {
        userServiceImpl.addUser(user);
        userServiceImpl.addUser(user2);
        itemServiceImpl.addItem(item, user.getId());
        bookingServiceImpl.bookingRequest(booking, user2.getId(), item.getId());

        List<BookingDto> bookingDtos = bookingServiceImpl.getBookingByOwnerAll(user.getId(), "ALL", 0, 20);
        BookingDto bookingDto = bookingDtos.get(0);

        assertThat(bookingDto.getId(), notNullValue());

        bookingServiceImpl.deleteAll();
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
        userServiceImpl.deleteUser(user2.getId());
    }

    @Test
    void getBookingByOwner() {
        userServiceImpl.addUser(user);
        userServiceImpl.addUser(user2);
        itemServiceImpl.addItem(item, user.getId());
        bookingServiceImpl.bookingRequest(booking, user2.getId(), item.getId());


        BookingDto bookingDto = bookingServiceImpl.getBookingByOwner(booking.getId(), user.getId());
        assertThat(bookingDto.getId(), notNullValue());

        bookingServiceImpl.deleteAll();
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
        userServiceImpl.deleteUser(user2.getId());
    }

}