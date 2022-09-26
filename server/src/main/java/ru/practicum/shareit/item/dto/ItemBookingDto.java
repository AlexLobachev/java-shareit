package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.LastBooking;
import ru.practicum.shareit.booking.dto.NextBooking;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Data
public class ItemBookingDto {
        private Long id;
        private String name;
        private String description;
        private Boolean available;
        private LastBooking lastBooking;
        private NextBooking nextBooking;
        private List<CommentsDto> comments;
        private User owner;
    }


