package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.RequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;


@Slf4j
@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final RequestClient requestClient;

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Получен запрос GET /requests/.");
        return requestClient.getUserRequests(userId);
    }

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                             @RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Получен запрос POST /requests/.");
        return requestClient.addRequest(itemRequestDto, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllRequests(@RequestHeader(USER_ID_HEADER) long userId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Получен запрос GET /requests/all.");
        return requestClient.findAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable long requestId,
                                                 @RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Получен запрос GET /requests/{requestId}.");
        return requestClient.getRequestById(requestId, userId);
    }
}