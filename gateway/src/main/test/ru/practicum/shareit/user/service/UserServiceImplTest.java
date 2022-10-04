package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.exception.ExceptionNotFoundUser;
import ru.practicum.shareit.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(properties = {"db.shareit"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = ShareItServer.class)
public class UserServiceImplTest {
    private User user;

    private final UserServiceImpl userServiceImpl;

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setName("Test");
        user.setEmail("Test@test.ru");
    }

    @Test
    void addUser() {
        user = userServiceImpl.addUser(user);
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), notNullValue());
        userServiceImpl.deleteUser(user.getId());

    }


    @Test
    void updateUserAndGet() {
        user = userServiceImpl.addUser(user);
        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo("Test"));
        assertThat(user.getEmail(), equalTo("Test@test.ru"));

        user.setName("Bobin");
        user.setEmail("Bobin@mail.ru");
        userServiceImpl.updateUser(user.getId(), user);

        assertThat(user.getName(), equalTo("Bobin"));
        assertThat(user.getEmail(), equalTo("Bobin@mail.ru"));
        userServiceImpl.deleteUser(user.getId());
    }

    @Test
    void deleteUser() {
        userServiceImpl.addUser(user);
        userServiceImpl.deleteUser(user.getId());

        final ExceptionNotFoundUser exception = Assertions.assertThrows(
                ExceptionNotFoundUser.class,
                () -> userServiceImpl.getUser(user.getId()));
        assertThat("Пользователя не существует", equalTo(exception.getMessage()));
    }
}