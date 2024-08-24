package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.service.CommentService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private static final String SHARER_USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;
    private final CommentService commentService;

    public ItemController(ItemService itemService,
                          CommentService commentService) {
        this.itemService = itemService;
        this.commentService = commentService;
    }

    @SneakyThrows
    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Long itemId,
                                           @RequestHeader(SHARER_USER_ID_HEADER) long userId) {
        return itemService.getItem(itemId, userId);
    }

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody ItemDto item,
                                           @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        return itemService.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId,
                                              @RequestBody ItemDto item,
                                              @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        return itemService.updateItem(itemId, item, userId);
    }

    @GetMapping
    public List<ItemDto> getItemsList(@RequestHeader(value = SHARER_USER_ID_HEADER) Long userId,
                                                      @RequestParam(name = "from", defaultValue = "0") int from,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        return itemService.getItemsList(userId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text,
                                                     @RequestParam(name = "from", defaultValue = "0") int from,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        return itemService.searchItem(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable long itemId,
                                                 @Valid @RequestBody CommentDto comment,
                                                 @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        return commentService.add(itemId, comment, userId);
    }
}