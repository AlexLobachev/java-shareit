package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.ShareItServer;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
@ContextConfiguration(classes = ShareItServer.class)
class UserDtoTest {
    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void userDto() throws IOException {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("demo-user@email.com");
        userDto.setName("demo");
        JsonContent<UserDto> result = json.write(userDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("demo");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("demo-user@email.com");
    }
}



