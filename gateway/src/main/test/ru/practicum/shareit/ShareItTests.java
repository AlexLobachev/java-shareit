package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;


@AutoConfigureTestDatabase
@TestPropertySource(properties = {"db.shareit"})
@ContextConfiguration(classes = ShareItServer.class)
class ShareItTests {
    @Test
    void contextLoads() {

    }
}