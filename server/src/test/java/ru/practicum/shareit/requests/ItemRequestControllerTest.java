package ru.practicum.shareit.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.servise.RequestServiceImpl;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RequestServiceImpl requestServiceImpl;
    @Autowired
    private MockMvc mockMvc;
    private ItemRequest itemRequest;

    @BeforeEach
    void beforeEach() {
        itemRequest = new ItemRequest(1L, "description", new User(), LocalDateTime.now());
    }

    @Test
    void addRequest() throws Exception {
        when(requestServiceImpl.addRequest(any(), anyLong()))
                .thenReturn(itemRequest);
        mockMvc.perform(post("/requests")
                        .content(objectMapper.writeValueAsString(itemRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequest.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }

    @Test
    void getRequest() throws Exception {
        List<ItemRequest> itemRequests = List.of(itemRequest);
        List<ItemRequestDto> itemRequestDtos = toRequestDto(itemRequests);
        when(requestServiceImpl.getRequest(anyLong()))
                .thenReturn(itemRequestDtos);
        mockMvc.perform(get("/requests")
                        .content(objectMapper.writeValueAsString(itemRequestDtos))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemRequestDtos)));
    }

    @Test
    void getRequestById() throws Exception {
        ItemRequestDto itemRequestDto = toRequestDto(itemRequest);
        when(requestServiceImpl.getRequestById(any(), anyLong()))
                .thenReturn(itemRequestDto);
        mockMvc.perform(get("/requests/1")
                        .content(objectMapper.writeValueAsString(itemRequestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequest.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }

    private List<ItemRequestDto> toRequestDto(List<ItemRequest> itemsRequest) {
        List<ItemRequestDto> itemRequestDto = new ArrayList<>();
        for (ItemRequest itemRequest : itemsRequest) {
            ItemRequestDto request = new ItemRequestDto();
            request.setId(itemRequest.getId());
            request.setDescription(itemRequest.getDescription());
            request.setCreated(itemRequest.getCreated());
            itemRequestDto.add(request);
        }
        return itemRequestDto;
    }

    private ItemRequestDto toRequestDto(ItemRequest itemsRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }
}