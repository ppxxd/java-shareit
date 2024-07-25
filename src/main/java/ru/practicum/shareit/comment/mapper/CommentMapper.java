package ru.practicum.shareit.comment.mapper;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment com) {
        return CommentDto.builder()
                .id(com.getId())
                .text(com.getText())
                .authorName(com.getUser().getName())
                .created(com.getCreated())
                .build();
    }

    public static Comment toComment(CommentDto com, Item item, User author) {
        return Comment.builder()
                .id(com.getId())
                .item(item)
                .user(author)
                .created(com.getCreated())
                .text(com.getText())
                .build();
    }
}