package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDtoBooking;
import ru.practicum.shareit.user.dto.UserDtoBooking;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private Status status;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserDtoBooking booker;
    private ItemDtoBooking item;
}
