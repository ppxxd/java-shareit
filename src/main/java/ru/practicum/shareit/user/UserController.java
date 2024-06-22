package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto user) {
        log.info("Получен запрос POST /users/.");
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        log.info("Получен запрос GET /users/{userId}.");
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @SneakyThrows
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto user) {
        log.info("Получен запрос PATCH /users/{userId}.");
        return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.OK);
    }

    @SneakyThrows
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        log.info("Получен запрос DELETE /users/{userId}.");
        userService.deleteUser(userId);
        return new ResponseEntity<>(String.format("User %s was deleted", userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("Получен запрос GET /users/.");
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
