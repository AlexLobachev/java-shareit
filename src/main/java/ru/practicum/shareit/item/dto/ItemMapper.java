package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.LastBooking;
import ru.practicum.shareit.booking.dto.NextBooking;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static ItemDtoBooking toItemDtoBooking(Item item) {
        return new ItemDtoBooking(item.getId(), item.getName());
    }

    public static ItemBookingDto toItemBookingDto(Item item, List<Booking> bookings, List<CommentsDto> comments, Long userId) {

        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(item.getId());
        itemBookingDto.setName(item.getName());
        itemBookingDto.setDescription(item.getDescription());
        itemBookingDto.setAvailable(item.getAvailable());
        itemBookingDto.setOwner(item.getOwner());
        itemBookingDto.setComments(comments);
        LastBooking lastBooking = new LastBooking();
        NextBooking nextBooking = new NextBooking();

        if (bookings.size() > 0) {
            if (bookings.get(0).getItem().getOwner().getId().equals(userId)) {
                if (bookings.size() > 1) {
                    lastBooking.setBookerId(bookings.get(0).getBooker().getId());
                    lastBooking.setId(bookings.get(0).getId());
                    itemBookingDto.setLastBooking(lastBooking);
                    nextBooking.setBookerId(bookings.get(1).getBooker().getId());
                    nextBooking.setId(bookings.get(1).getId());
                    itemBookingDto.setNextBooking(nextBooking);
                }
            }
        }
        return itemBookingDto;
    }

}
