package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
@ContextConfiguration(classes = ShareItServer.class)
class ItemControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemController itemController;
    @Autowired
    private MockMvc mockMvc;
    private Item item;

    @BeforeEach
    void beforeEach() {
        item = new Item(1L, "Hammer", "Молоток", true, new User(), 1L);
    }

    @Test
    void addItem() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        ItemDto itemDto = itemDto(item);
        when(itemController.addItem(any(), anyLong()))
                .thenReturn(responseBuilder.body(itemDto));
        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    void updateItem() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        when(itemController.updateItem(anyLong(), any(), anyLong()))
                .thenReturn(responseBuilder.body(item));
        mockMvc.perform(patch("/items/1")
                        .content(objectMapper.writeValueAsString(item))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    void getItem() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        ItemBookingDto itemBookingDto = itemBookingDto(item);
        when(itemController.getItem(anyLong(), anyLong()))
                .thenReturn(responseBuilder.body(itemBookingDto));
        mockMvc.perform(get("/items/1")
                        .content(objectMapper.writeValueAsString(itemBookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(item.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(item.getName())))
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    void getItemsUser() throws Exception {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(200);
        List<ItemBookingDto> itemBookingDto = List.of(itemBookingDto(item));
        when(itemController.getItemsUser(anyLong()))
                .thenReturn(responseBuilder.body(itemBookingDto));
        mockMvc.perform(get("/items")
                        .content(objectMapper.writeValueAsString(itemBookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemBookingDto)));
    }

    private ItemDto itemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setRequestId(item.getRequestId());
        return itemDto;
    }

    private ItemBookingDto itemBookingDto(Item item) {
        ItemBookingDto itemBookingDto = new ItemBookingDto();
        itemBookingDto.setId(item.getId());
        itemBookingDto.setName(item.getName());
        itemBookingDto.setDescription(item.getDescription());
        itemBookingDto.setAvailable(item.getAvailable());
        return itemBookingDto;
    }
}
