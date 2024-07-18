package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.service.ItemBookingService;
import ru.practicum.shareit.comment.service.CommentService;
import ru.practicum.shareit.exception.ObjectAccessException;
import ru.practicum.shareit.exception.ObjectExistException;
import ru.practicum.shareit.exception.ObjectUpdateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemBookingService bookingService;
    private final CommentService commentService;
    private final ItemRepository repository;

    @Override
    public ItemDto addItem(ItemDto item, Long idUser) {
        item.setOwner(UserMapper.toUser(userService.getUser(idUser)));
        return ItemMapper.toItemDto(repository.save(ItemMapper.toItem(item)));
    }

    @Override
    public ItemDto getItem(Long id, long userId) {
        ItemDto item = ItemMapper.toItemDto(getItemById(id));
        item.setLastBooking(item.getOwner().getId() == userId ? bookingService.getLastBooking(item.getId()) : null);
        item.setNextBooking(item.getOwner().getId() == userId ? bookingService.getNextBooking(item.getId()) : null);
        item.setComments(commentService.getAllCommentsByItemId(id) == null ? new ArrayList<>() : commentService.getAllCommentsByItemId(id));
        return item;
    }

    @Override
    public List<ItemDto> getItemsList(Long idUser) {
        return repository.findAllByOwnerId(idUser).stream()
                .map(o -> {
                    o.setLastBooking(bookingService.getLastBooking(o.getId()));
                    o.setNextBooking(bookingService.getNextBooking(o.getId()));
                    o.setComments(commentService.getAllCommentsByItemId(o.getId()));
                    return ItemMapper.toItemDto(o);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDto updateItem(Long id, ItemDto item, Long idUser) {
        Item itemToUpdate = getItemById(id);
        checkItemOnUpdate(itemToUpdate, item, idUser);

        itemToUpdate.setName(item.getName() == null ? itemToUpdate.getName() : item.getName());
        itemToUpdate.setDescription(item.getDescription() == null ? itemToUpdate.getDescription() : item.getDescription());
        itemToUpdate.setAvailable(item.getAvailable() == null ? itemToUpdate.isAvailable() : item.getAvailable());

        repository.save(itemToUpdate);
        return ItemMapper.toItemDto(itemToUpdate);
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        if (text.isBlank())
            return new ArrayList<>();

        return repository.findAllByDescriptionContainingIgnoreCaseOrNameContainingIgnoreCase(text, text)
                .stream()
                .filter(Item::isAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void checkItemOwner(Item item, Long userId) {
        if (item.getOwner().getId() != userId) {
            throw new ObjectAccessException("Вы можете изменять только свои вещи!");
        }
    }

    private void checkItemOnUpdate(Item itemToUpdate, ItemDto item, Long userId) {
        checkItemOwner(itemToUpdate, userId);

        if (item.getOwner() != null || item.getRequest() != null)
            throw new ObjectUpdateException("Эти поля не могут быть обновлены!");
    }

    private Item getItemById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectExistException("Вещь не существует!"));
    }
}