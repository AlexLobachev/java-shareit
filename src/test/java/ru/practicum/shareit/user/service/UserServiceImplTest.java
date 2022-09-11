package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.exсeption.ExceptionNotFoundUser;
import ru.practicum.shareit.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.shareit"})

public class UserServiceImplTest {
    private User user;
    private final UserServiceImpl userServiceImpl;

    @Test
    void addUser() {
        user = userServiceImpl.addUser(new User(1L, "Robin", "Robin@mail.ru"));
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), notNullValue());

    }


    @Test
    void updateUserAndGet() {
        user = userServiceImpl.getUser(1L);
        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo("Robin"));
        assertThat(user.getEmail(), equalTo("Robin@mail.ru"));


        user.setName("Bobin");
        user.setEmail("Bobin@mail.ru");
        userServiceImpl.updateUser(1L, user);

        assertThat(user.getName(), equalTo("Bobin"));
        assertThat(user.getEmail(), equalTo("Bobin@mail.ru"));
    }

    @Test
    void deleteUser() {
        userServiceImpl.addUser(new User(2L, "Robin", "Robin@mail.ru"));
        userServiceImpl.deleteUser(2L);

        final ExceptionNotFoundUser exception = Assertions.assertThrows(
                ExceptionNotFoundUser.class,
                () -> userServiceImpl.getUser(2L));
        assertThat("Пользователя не существует", equalTo(exception.getMessage()));
    }
}