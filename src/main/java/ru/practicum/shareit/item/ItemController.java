package ru.practicum.shareit.item;

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

import javax.validation.Valid;
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
    public ResponseEntity<ItemDto> getItem(@PathVariable Long itemId,
                                           @RequestHeader(SHARER_USER_ID_HEADER) long userId) {
        log.info("Получен запрос GET /items/{itemId}.");
        return new ResponseEntity<>(itemService.getItem(itemId, userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto item,
                                           @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        log.info("Получен запрос POST /items/.");
        return new ResponseEntity<>(itemService.addItem(item, userId), HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long itemId,
                                              @RequestBody ItemDto item,
                                              @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        log.info("Получен запрос PATCH /items/{itemId}.");
        return new ResponseEntity<>(itemService.updateItem(itemId, item, userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemsList(@RequestHeader(value = SHARER_USER_ID_HEADER) Long userId,
                                                      @RequestParam(name = "from", defaultValue = "0") int from,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Получен запрос GET /items/.");
        return new ResponseEntity<>(itemService.getItemsList(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text,
                                                     @RequestParam(name = "from", defaultValue = "0") int from,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Получен запрос GET /items/search.");
        return new ResponseEntity<>(itemService.searchItem(text, from, size), HttpStatus.OK);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable long itemId,
                                                 @Valid @RequestBody CommentDto comment,
                                                 @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId) {
        log.info("Получен запрос POST /items/{itemId}/comment.");
        return new ResponseEntity<>(commentService.add(itemId, comment, userId), HttpStatus.OK);
    }
}