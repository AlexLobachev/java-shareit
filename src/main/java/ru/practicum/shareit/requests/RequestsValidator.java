package ru.practicum.shareit.requests;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exсeption.ExclusionInvalidRequest;
@Service
public class RequestsValidator {
    public void checkParam(Long from, Long size){
        if (from!=null&&size!=null) {
            if (from < 0 || size <= 0) {
                throw new ExclusionInvalidRequest("Параметры не могут быть <0 или размер не может быть равным 0");
            }
        }
    }
}
