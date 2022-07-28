package ru.practicum.shareit.item.servise;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.InMemoryItemStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    InMemoryItemStorage inMemoryItemStorage;

    public List<Item> getItemsUser(Integer idOwner) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : inMemoryItemStorage.get()) {
            if (item.getOwner().equals(idOwner)) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public List<Item> getSearchName(String text) {
        List<Item> itemList = new ArrayList<>();
        if (!text.isBlank()) {
            for (Item item : inMemoryItemStorage.get()) {
                if (item.getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase(Locale.ROOT).contains(text.toLowerCase())
                                && item.getAvailable()) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }


}
