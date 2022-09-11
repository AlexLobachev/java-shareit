package ru.practicum.shareit.item.servise;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.shareit"})

public
class ItemServiceImplTest {

    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;


    @Test
    void addItem() {
        User user = userServiceImpl.getUser(1L);
        ItemDto itemDto = itemServiceImpl.addItem(new Item(1L, "Hammer", "description", true, user, null), 1L);
        assertThat(itemDto.getId(), notNullValue());
        assertThat(itemDto.getName(), equalTo("Hammer"));
        assertThat(itemDto.getDescription(), equalTo("description"));
        assertThat(itemDto.getAvailable(), notNullValue());

    }


    @Test
    void getItemsUser() {
        User user = userServiceImpl.getUser(1L);
        itemServiceImpl.addItem(new Item(1L, "Hammer", "description", true, user, null), 1L);


        List<ItemBookingDto> itemBookingDtos = itemServiceImpl.getItemsUser(1L);

        ItemBookingDto itemBookingDto = itemBookingDtos.get(0);

        assertThat(itemBookingDto.getName(), equalTo("Hammer"));
    }

    @Test
    void getSearchName() {
        User user = userServiceImpl.getUser(1L);
        itemServiceImpl.addItem(new Item(1L, "Hammer", "description", true, user, null), 1L);

        List<Item> items = itemServiceImpl.getSearchName("description");

        ItemBookingDto itemBookingDto = itemBookingDto(items.get(0));

        assertThat(itemBookingDto.getDescription(), equalTo("description"));
    }


    @Test
    void updateItemAndGet() {
        ItemBookingDto itemBookingDto = itemServiceImpl.getItem(1L, 1L);
        assertThat(itemBookingDto.getId(), notNullValue());
        assertThat(itemBookingDto.getName(), equalTo("Hammer"));
        assertThat(itemBookingDto.getDescription(), equalTo("description"));
        assertThat(itemBookingDto.getAvailable(), notNullValue());
        Item item = itemDto(itemBookingDto);
        item.setName("Hammer2");
        item.setDescription("description2");
        item.setAvailable(false);
        itemServiceImpl.updateItem(1L, item, 1L);
        itemBookingDto = itemServiceImpl.getItem(1L, 1L);
        assertThat(itemBookingDto.getName(), equalTo("Hammer2"));

    }


    private Item itemDto(ItemBookingDto itemBookingDto) {
        Item item = new Item();
        item.setId(itemBookingDto.getId());
        item.setName(itemBookingDto.getName());
        item.setDescription(itemBookingDto.getDescription());
        item.setAvailable(itemBookingDto.getAvailable());
        return item;
    }

    private ItemBookingDto itemBookingDto(Item item) {
        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(item.getId());
        itemBookingDto.setName(item.getName());
        itemBookingDto.setDescription(item.getDescription());
        itemBookingDto.setAvailable(item.getAvailable());
        return itemBookingDto;
    }
}