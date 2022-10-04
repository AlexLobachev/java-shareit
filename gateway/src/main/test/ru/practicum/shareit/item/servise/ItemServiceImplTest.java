package ru.practicum.shareit.item.servise;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.ShareItServer;
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
@ContextConfiguration(classes = ShareItServer.class)
public class ItemServiceImplTest {
    private final ItemServiceImpl itemServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private User user;
    private Item item;

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setName("Test");
        user.setEmail("Test@tests.ru");
        item = new Item();
        item.setName("Hammer");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(user);
    }

    @Test
    void addItem() {
        userServiceImpl.addUser(user);
        ItemDto itemDto = itemServiceImpl.addItem(item, user.getId());
        assertThat(itemDto.getId(), notNullValue());
        assertThat(itemDto.getName(), equalTo("Hammer"));
        assertThat(itemDto.getDescription(), equalTo("description"));
        assertThat(itemDto.getAvailable(), notNullValue());
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
    }

    @Test
    void getItemsUser() {
        userServiceImpl.addUser(user);
        itemServiceImpl.addItem(item, user.getId());
        List<ItemBookingDto> itemBookingDtos = itemServiceImpl.getItemsUser(user.getId());
        ItemBookingDto itemBookingDto = itemBookingDtos.get(0);
        assertThat(itemBookingDto.getName(), equalTo("Hammer"));
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
    }

    @Test
    void getSearchName() {
        userServiceImpl.addUser(user);
        itemServiceImpl.addItem(item, user.getId());
        List<Item> items = itemServiceImpl.getSearchName("description");
        ItemBookingDto itemBookingDto = itemBookingDto(items.get(0));
        assertThat(itemBookingDto.getDescription(), equalTo("description"));
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
    }

    @Test
    void updateItemAndGet() {
        userServiceImpl.addUser(user);
        itemServiceImpl.addItem(item, user.getId());
        ItemBookingDto itemBookingDto = itemServiceImpl.getItem(item.getId(), user.getId());
        assertThat(itemBookingDto.getId(), notNullValue());
        assertThat(itemBookingDto.getName(), equalTo("Hammer"));
        assertThat(itemBookingDto.getDescription(), equalTo("description"));
        assertThat(itemBookingDto.getAvailable(), notNullValue());
        Item item = itemDto(itemBookingDto);
        item.setName("Hammer2");
        item.setDescription("description2");
        item.setAvailable(false);
        itemServiceImpl.updateItem(item.getId(), item, user.getId());
        itemBookingDto = itemServiceImpl.getItem(item.getId(), user.getId());
        assertThat(itemBookingDto.getName(), equalTo("Hammer2"));
        itemServiceImpl.deleteItem();
        userServiceImpl.deleteUser(user.getId());
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