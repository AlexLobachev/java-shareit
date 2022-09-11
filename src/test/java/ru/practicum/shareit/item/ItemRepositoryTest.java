package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@DataJpaTest
@Transactional
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    private Item item;

    @BeforeEach
    void beforeEach() {
        item = new Item();
        item.setName("demo");
        item.setDescription("demoDescription");
        item.setAvailable(true);
    }

    @Test
    void updateItemByAll() {
        itemRepository.save(item);
        itemRepository.updateItemByAll("demo2", "demoDescription2", false, 2L);
        item = itemRepository.findById(2L).orElse(new Item());

        Assertions.assertEquals(item.getName(), "demo2");
        Assertions.assertEquals(item.getDescription(), "demoDescription2");

    }

    @Test
    void findAllByName() {
        List<Item> items = itemRepository.findAllByName("demo2");
        Assertions.assertNotNull(items);
    }
}