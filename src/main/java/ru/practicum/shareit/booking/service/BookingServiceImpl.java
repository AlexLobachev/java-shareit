package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingValidator;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exсeption.ExceptionNotFoundUser;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.servise.ItemServiceImpl;
import ru.practicum.shareit.requests.OffsetLimitPageable;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.dto.BookingMapper.toBookingDto;
import static ru.practicum.shareit.booking.model.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor

@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final BookingValidator validator;

    public Booking bookingRequest(Booking booking, Long id,Long itemId) {
        booking.setBooker(userServiceImpl.getUser(id));
        booking.setItem(itemRepository.findById(itemId).orElse(new Item()));
        validator.checkPost(booking,itemId);
        return bookingRepository.save(booking);
    }


    public Booking confirmOrRejectBooking(Long bookingId, Boolean status, Long ownerId) {
        validator.checkIdOwnerAndStatus(bookingRepository.findById(bookingId).orElse(new Booking()), ownerId);
        bookingRepository.updateStatus(validator.decryptsTheStatus(status), bookingId);
        return bookingRepository.findById(bookingId).orElse(new Booking());
    }


    public BookingDto getBookingByOwner(Long id, Long ownerId) {
        if (bookingRepository.findById(id).isEmpty()) {
            throw new ExceptionNotFoundUser("Бронирование не найдено");
        }
        return validator.checkGetBookerAndUser(toBookingDto((bookingRepository.findById(id).orElse(new Booking()))), ownerId);
    }

    public List<BookingDto> getBookingByOwnerAll(Long ownerId, String state, Integer from, Integer size) {
        userServiceImpl.getUser(ownerId);
        itemServiceImpl.getItemsUser(ownerId);
        Pageable pageable = OffsetLimitPageable.of(from, size);
        switch (state) {
            case ("FUTURE"):
                return bookingRepository.findByStatusNotAndStatusNotOrderByIdDesc(REJECTED, CANCELED).stream().filter(x -> x.getItem().getOwner().getId().equals(ownerId)).map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("CURRENT"):
                return bookingRepository.findByOrderCurrent(LocalDateTime.now(), ownerId).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("REJECTED"):
                return bookingRepository.findByStatusOrderByIdDesc(REJECTED).stream().filter(x -> x.getItem().getOwner().getId().equals(ownerId)).map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("PAST"):

                return bookingRepository.findByOrderPast(ownerId, LocalDateTime.now()).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("ALL"):
                return bookingRepository.findAllByOrderByIdDesc(ownerId,pageable).stream().filter(x -> x.getItem().getOwner().getId().equals(ownerId)).map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("WAITING"):
                return bookingRepository.findByStatusOrderByIdDesc(WAITING).stream().filter(x -> x.getItem().getOwner().getId().equals(ownerId)).map(BookingMapper::toBookingDto).collect(Collectors.toList());
            default:
                throw new ValidationException("Unknown state: " + state);
        }
    }

    public List<BookingDto> getBookingByUserAll(String state, Long userId, Integer from,Integer size) {
        userServiceImpl.getUser(userId);
        Pageable pageable = OffsetLimitPageable.of(from, size, Sort.by("id"));
        switch (state) {
            case ("FUTURE"):
                return bookingRepository.findByStatusNotAndStatusNotOrderByIdDesc(REJECTED, CANCELED).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("CURRENT"):
                return bookingRepository.findByUserCurrent(LocalDateTime.now(), userId).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("REJECTED"):
                return bookingRepository.findAllByBooker_IdAndStatusOrderByIdDesc(userId, REJECTED).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("PAST"):
                return bookingRepository.findByUserPast(userId, LocalDateTime.now()).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("ALL"):
                return bookingRepository.findByBooker_idOrderByIdDesc(userId, pageable).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            case ("WAITING"):
                return bookingRepository.findAllByBooker_IdAndStatusOrderByIdDesc(userId, WAITING).stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
            default:
                throw new ValidationException("Unknown state: " + state);
        }
    }

}
