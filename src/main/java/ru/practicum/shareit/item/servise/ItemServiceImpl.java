package ru.practicum.shareit.item.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exteption.ExclusionInvalidRequest;
import ru.practicum.shareit.item.CommentsRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemValidator;
import ru.practicum.shareit.item.dto.CommentsDto;
import ru.practicum.shareit.item.dto.CommentsMapper;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.model.Comments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.dto.ItemMapper.toItemBookingDto;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {
    private final UserServiceImpl userService;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final ItemValidator itemValidator;
    private final CommentsRepository commentsRepository;

    public Item addItem(Item item, Long idOwner) {
        item.setOwner(userService.getUser(idOwner));
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item item, Long idOwner) {
        item.setOwner(userService.getUser(idOwner));
        itemValidator.checkUser(idOwner, id);
        if (item.getName() != null && item.getDescription() != null && item.getAvailable() != null) {
            itemRepository.updateItemByAll(item.getName(), item.getDescription(), item.getAvailable(), id);
            log.debug("Вещь {} успешно изменена", id);
        } else if (item.getName() != null) {
            itemRepository.updateItemByName(item.getName(), id);
            log.debug("Имя вещи {} успешно изменено", id);
        } else if (item.getDescription() != null) {
            itemRepository.updateItemByDescription(item.getDescription(), id);
            log.debug("Описание вещи {} успешно изменено", id);
        } else if (item.getAvailable() != null) {
            itemRepository.updateItemByAvailable(item.getAvailable(), id);
            log.debug("Статус вещи {} успешно изменен", id);
        }
        return itemRepository.findById(id).orElse(new Item());
    }

    public ItemBookingDto getItem(Long id, Long userId) {
        itemValidator.checkItem(id);
        Item item = itemRepository.findById(id).orElse(new Item());
        List<Booking> bookings = bookingRepository.findByItem_idOrderByStartAsc(id);
        List<CommentsDto> comments = CommentsMapper.toCommentsDto(commentsRepository.findAllByItem_Id(id));
        return toItemBookingDto(item, bookings, comments, userId);
    }

    public List<ItemBookingDto> getItemsUser(Long idOwner) {
        List<Item> items = itemRepository.findByOwner_id(idOwner);
        List<ItemBookingDto> itemBookingDto = new ArrayList<>();
        for (Item item : items) {
            itemBookingDto.add(getItem(item.getId(), idOwner));
        }
        return itemBookingDto.stream().sorted(Comparator.comparingLong(ItemBookingDto::getId)).collect(Collectors.toList());
    }

    public List<Item> getSearchName(String text) {
        List<Item> list = new ArrayList<>();
        if (!text.isBlank()) {
            list = itemRepository.findAllByName(text.toLowerCase());
        }
        return list;
    }

    public Comments addComment(Long id, Long idOwner, Comments comments) {
        if (!bookingRepository.findByItem_id(id, LocalDateTime.now()).contains(idOwner)) {
            throw new ExclusionInvalidRequest("Пользовтаель не брал эту вещь в аренду");
        }
        comments.setAuthorName(userService.getUser(idOwner).getName());
        comments.setItem(itemRepository.getById(id));
        return commentsRepository.save(comments);
    }

}
