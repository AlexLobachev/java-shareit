package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentsDto {
    private Long id;
    @NotBlank
    private String text;
    private String authorName;

}
