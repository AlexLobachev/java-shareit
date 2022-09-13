package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.model.Status.APPROVED;

@DataJpaTest
@Transactional
@TestPropertySource(properties = {"db.shareit"})
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    private Booking booking;

    @BeforeEach
    void beforeEach() {
        User user = new User();
        user.setEmail("demo-user@email.com");
        user.setName("demo");
        userRepository.save(user);
        Item item = new Item();
        item.setName("demo");
        item.setDescription("demoDescription");
        item.setAvailable(true);
        item.setOwner(userRepository.findById(user.getId()).orElse(new User()));
        itemRepository.save(item);
        booking = new Booking();
        booking.setStart(LocalDateTime.now().plusMinutes(1));
        booking.setEnd(LocalDateTime.now().plusDays(1));
        booking.setBooker(userRepository.findById(user.getId()).orElse(new User()));
        booking.setItem(itemRepository.findById(item.getId()).orElse(new Item()));
        bookingRepository.save(booking);
    }

    @Test
    void updateStatus() {
        bookingRepository.updateStatus(APPROVED, booking.getId());
        booking = bookingRepository.findById(booking.getId()).orElse(new Booking());
        Assertions.assertEquals(booking.getStatus(), APPROVED);
    }

    @Test
    void findByOrderPast() {
        List<Booking> bookings = bookingRepository.findByOrderPast(booking.getId(), LocalDateTime.now().plusSeconds(10));
        Assertions.assertNotNull(bookings);
    }

    @Test
    void findByUserPast() {
        List<Booking> bookings = bookingRepository.findByOrderPast(booking.getId(), LocalDateTime.now().plusSeconds(10));
        Assertions.assertNotNull(bookings);
    }

}
