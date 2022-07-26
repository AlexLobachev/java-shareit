package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = ShareItServer.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    private User user;
    private List<User> users = new ArrayList<>();


    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("Test@test.ru");
    }

    @Test
    void getAllUsers() throws Exception {
        users.add(user);
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        when(userController.getAllUsers())
                .thenReturn(responseBuilder.body(users));
        mockMvc.perform(get("/users")
                        .content(objectMapper.writeValueAsString(users))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));

    }

    @Test
    void getUser() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        when(userController.getUser(anyLong()))
                .thenReturn(responseBuilder.body(user));
        mockMvc.perform(get("/users/" + user.getId())
                        .content(objectMapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void addUser() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        when(userController.addUser(any()))
                .thenReturn(responseBuilder.body(user));
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(user.getName())))

                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }


}