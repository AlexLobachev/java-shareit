package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exception.ExclusionInvalidRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> bookingRequest(BookingDto booking, Long userId, Long itemId) {
        if (booking.getStart().isAfter(booking.getEnd())){
            throw new ExclusionInvalidRequest("Время окончания не может быть раньше времени старта");
        }
        return post("", userId,booking);
    }


    public ResponseEntity<Object> confirmOrRejectBooking(Long bookingId, Boolean approved, Long ownerId) throws UnsupportedEncodingException {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );
        return patch("/"+bookingId+"?approved={approved}",ownerId,parameters);
    }


    public ResponseEntity<Object> getBookingByOwner(Long id, Long ownerId) {
        return get("/"+id,ownerId);
    }

    public ResponseEntity<Object> getBookingByOwnerAll(BookingState state, Long ownerId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&from={from}&size={size}", ownerId, parameters);
    }

    public ResponseEntity<Object> getBookingByUserAll(BookingState state, Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }


}
