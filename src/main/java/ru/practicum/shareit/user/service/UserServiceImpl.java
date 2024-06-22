package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectCreateException;
import ru.practicum.shareit.exception.ObjectExistException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Map<Long, User> users;
    private long id = 1;

    @Override
    public UserDto addUser(UserDto user) {
        log.info(String.format("Create user: %s", user));
        checkUser(id, user);
        user.setId(id);
        users.put(id, UserMapper.toUser(user));
        return getUser(id++);
    }

    @Override
    public UserDto getUser(Long id) {
        log.info(String.format("Get user by id: %s", id));
        if (!users.containsKey(id))
            throw new ObjectExistException("User doesn't exists");
        log.info("User: {}", UserMapper.toUserDto(users.get(id)));
        return UserMapper.toUserDto(users.get(id));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto user) {
        log.info(String.format("Start update idUser = %s", user.getId()));
        if (!users.containsKey(userId))
            throw new ObjectExistException("User doesn't exists");
        checkUser(userId, user);

        User userToUpdate = users.get(userId);

        userToUpdate.setEmail(user.getEmail() == null ? userToUpdate.getEmail() : user.getEmail());
        userToUpdate.setName(user.getName() == null ? userToUpdate.getName() : user.getName());

        users.put(userId, userToUpdate);
        log.info("User after update: {}", getUser(userId));
        return getUser(userId);
    }

    @Override
    public void deleteUser(Long id) {
        if (users.remove(id) == null)
            throw new ObjectExistException("User doesn't exists");
    }

    @Override
    public List<UserDto> getAll() {
        return users.values().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private void checkUser(Long userId, UserDto user) {
        log.info("Start check User");
        boolean isUnique = users.values().stream()
                .anyMatch(o -> o.getEmail().equals(user.getEmail()) && userId != o.getId());
        if (isUnique)
            throw new ObjectCreateException("User with this e-mail already exists");
        log.info("User is correct");
    }
}
