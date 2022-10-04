package ru.practicum.shareit.requests.servise;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.ShareItServer;
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
@ContextConfiguration(classes = ShareItServer.class)
class RequestServiceImplTest {
    private final RequestServiceImpl requestServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private User user;
    private ItemRequest itemRequest;
    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setName("Robin");
        user.setEmail("Robin@mail.ru");
        userServiceImpl.addUser(user);

        itemRequest = new ItemRequest();
        itemRequest.setDescription("Нужно что то");
        itemRequest.setRequestor(null);
        itemRequest.setCreated(LocalDateTime.now());

    }

    @Test
    void addRequest() {
        ItemRequest itemRequest1 = requestServiceImpl.addRequest(itemRequest, user.getId());
        assertThat(itemRequest1.getDescription(), equalTo("Нужно что то"));
    }

    @Test
    void getRequest() {
        requestServiceImpl.addRequest(itemRequest, user.getId());
        List<ItemRequestDto> itemRequestDtos = requestServiceImpl.getRequest(user.getId());
        System.out.println(itemRequestDtos);
        assertThat(itemRequestDtos.get(0).getDescription(), equalTo("Нужно что то"));
    }

    @Test
    void getAllRequest() {
        userServiceImpl.addUser(new User(1L, "Robin", "Robin@mail.ru"));
        userServiceImpl.addUser(new User(2L, "Robin", "Robin@maill.ru"));
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
