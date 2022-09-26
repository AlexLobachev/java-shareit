package ru.practicum.shareit.requests.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ItemRequestDto {
    private Long id;
    @NotBlank
    private String description;
    private LocalDateTime created = LocalDateTime.now();

}
