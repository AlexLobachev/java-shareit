package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDtoBooking;
import ru.practicum.shareit.user.dto.UserDtoBooking;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Status status;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserDtoBooking booker;
    private ItemDtoBooking item;
}
