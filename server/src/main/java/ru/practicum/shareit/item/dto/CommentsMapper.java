package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comments;

import java.util.ArrayList;
import java.util.List;

public class CommentsMapper {
    public static List<CommentsDto> toCommentsDto(List<Comments> comments) {
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (Comments comment : comments) {
            CommentsDto commentsDto = new CommentsDto();
            commentsDto.setId(comment.getId());
            commentsDto.setText(comment.getText());
            commentsDto.setAuthorName(comment.getAuthorName());
            commentsDtoList.add(commentsDto);
        }
        return commentsDtoList;
    }
}
