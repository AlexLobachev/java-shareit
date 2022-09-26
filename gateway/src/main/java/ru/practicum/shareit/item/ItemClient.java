package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentsDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserClient;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";
    private final UserClient userClient;

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder, UserClient userClient) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
        this.userClient = userClient;
    }

    public ResponseEntity<Object> addItem(ItemDto itemDto, Long idOwner) {
        userClient.getUser(idOwner);
        return post("", idOwner, itemDto);
    }

    public ResponseEntity<Object> updateItem(Long id, ItemDto itemDto, Long idOwner) {
        return patch("/" + id, idOwner, itemDto);
    }

    public ResponseEntity<Object> getItem(Long id, Long userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> getItemsUser(Long idOwner) {
        return get("", idOwner);
    }

    public ResponseEntity<Object> getSearchName(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> addComment(Long id, Long idOwner, CommentsDto commentsDto) {
        return post("/" + id + "/comment", idOwner, commentsDto);
    }

}
