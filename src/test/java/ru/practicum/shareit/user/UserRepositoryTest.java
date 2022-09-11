package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;

@DataJpaTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void updateUserByAll() {

        User user = new User();
        user.setEmail("demo-user@email.com");
        user.setName("demo");
        repository.save(user);
        repository.updateUserByAll("demo2", "demo2-user@email.com", 1L);
        user = repository.findById(1L).orElse(new User());

        Assertions.assertEquals(user.getName(), "demo");

    }
}