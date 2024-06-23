package ru.practicum.shareit.item;

import javax.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @SneakyThrows
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Long itemId) {
        log.info("Получен запрос GET /items/{itemId}.");
        return new ResponseEntity<>(itemService.getItem(itemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto item,
                                           @RequestHeader(value = USER_ID_HEADER) Long userId) {
        log.info("Получен запрос POST /items/.");
        return new ResponseEntity<>(itemService.addItem(item, userId), HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long itemId,
                                              @RequestBody ItemDto item,
                                              @RequestHeader(value = USER_ID_HEADER) Long userId) {
        log.info("Получен запрос PATCH /items/{itemId}.");
        return new ResponseEntity<>(itemService.updateItem(itemId, item, userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemsList(@RequestHeader(value = USER_ID_HEADER) Long userId) {
        log.info("Получен запрос GET /items/.");
        return new ResponseEntity<>(itemService.getItemsList(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text) {
        log.info("Получен запрос GET /items/search/.");
        return new ResponseEntity<>(itemService.searchItem(text), HttpStatus.OK);
    }
}
