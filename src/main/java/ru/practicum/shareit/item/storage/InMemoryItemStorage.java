package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exteption.ExceptionNotFoundUser;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.storage.InMemoryUserStorage;

import java.util.*;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();
    private final InMemoryUserStorage inMemoryUserStorage;
    private Integer id = 0;

    public Item addItem(Item item, Integer idOwner) {
        item.setOwner(notFoundUser(idOwner));
        item.setId(++id);
        items.put(item.getId(), item);
        return item;

    }

    public Item updateItem(Integer id, Item item, Integer idOwner) {
        if (items.containsKey(id)) {
            item.setOwner(notFoundUser(idOwner));
            withOtherUser(idOwner, id);
            Item itemPatch = items.get(id);

            if (item.getName() != null && item.getDescription() != null && item.getAvailable() != null) {
                items.put(id, item);
                log.debug("Вещь {} успешно изменена", id);
            } else if (item.getName() != null) {
                itemPatch.setName(item.getName());
                items.put(id, itemPatch);
                log.debug("Имя вещи {} успешно изменено", id);
                return itemPatch;
            } else if (item.getDescription() != null) {
                itemPatch.setDescription(item.getDescription());
                items.put(id, itemPatch);
                log.debug("Описание вещи {} успешно изменено", id);
                return itemPatch;
            } else if (item.getAvailable() != null) {
                itemPatch.setAvailable(item.getAvailable());
                items.put(id, itemPatch);
                log.debug("Статус вещи {} успешно изменен", id);
                return itemPatch;
            }
            return item;
        }
        log.debug("Вещь {} не найдена", id);
        return item;
    }


    public Item getItem(Integer id) {
        return items.get(id);
    }

    private Integer notFoundUser(Integer idOwner) {
        if (!inMemoryUserStorage.containsKey(idOwner)) {
            throw new ExceptionNotFoundUser("Пользователь не найден");
        }
        return idOwner;
    }

    private void withOtherUser(Integer idOwner, Integer id) {
        if (!items.get(id).getOwner().equals(idOwner)) {
            throw new ExceptionNotFoundUser("Пользователю не принадлежит эта вещь");
        }
    }

    public Collection<Item> get() {
        return items.values();
    }


}
