package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exteption.ExceptionNotFoundUser;
@AllArgsConstructor
@Service
public class ItemValidator {
    private final ItemRepository itemRepository;
    public void checkUser(Long idOwner, Long id) {
        if (!itemRepository.getById(id).getOwner().getId().equals(idOwner)) {
            throw new ExceptionNotFoundUser("Пользователю не принадлежит эта вещь");
        }
    }
    public void checkItem(Long id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ExceptionNotFoundUser("Вещи не существует");
        }
    }
    }



