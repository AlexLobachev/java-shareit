package ru.practicum.shareit.booking.dto.model;


import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
@Data
public class Booking {
    Integer id;
    LocalDate start;
    LocalDate end;
    Item item;
    User booker;
    Boolean status;

}
