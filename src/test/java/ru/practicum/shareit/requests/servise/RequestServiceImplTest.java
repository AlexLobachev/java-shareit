package ru.practicum.shareit.requests.servise;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureTestDatabase

@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RequestServiceImplTest {

    private final RequestServiceImpl requestServiceImpl;
    private final UserServiceImpl userServiceImpl;


    @Test
    void addRequest() {
        userServiceImpl.addUser(new User(1L, "Robin", "Robin@mail.ru"));
        ItemRequest itemRequest = requestServiceImpl.addRequest(new ItemRequest(1L, "Нужно что то", null, LocalDateTime.now()), 1L);
        assertThat(itemRequest.getDescription(), equalTo("Нужно что то"));

    }

    @Test
    void getRequest() {
        List<ItemRequestDto> itemRequestDtos = requestServiceImpl.getRequest(1L);
        assertThat(itemRequestDtos.get(0).getDescription(), equalTo("Нужно что то"));

    }

    @Test
    void getAllRequest() {

        userServiceImpl.addUser(new User(1L, "Robin", "Robin@mail.ru"));
        userServiceImpl.addUser(new User(2L, "Robin", "Robin@mail.ru"));
        requestServiceImpl.addRequest(new ItemRequest(1L, "Нужно что то", null, LocalDateTime.now()), 1L);

        List<ItemRequestDto> itemRequestDtos = requestServiceImpl.getAllRequest(0, 20, 2L);
        assertThat(itemRequestDtos.get(0).getDescription(), equalTo("Нужно что то"));
    }

    @Test
    void getRequestById() {
        ItemRequestDto itemRequestDto = requestServiceImpl.getRequestById(1L, 1L);
        assertThat(itemRequestDto.getDescription(), equalTo("Нужно что то"));
    }
}