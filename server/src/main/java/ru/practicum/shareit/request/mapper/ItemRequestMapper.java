package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest request, List<RequestItemDto> items) {
        return ItemRequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(items)
                .build();
    }

    public static ItemRequest toRequest(ItemRequestDto request, User requester, List<Item> items) {
        return ItemRequest.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .requestor(requester)
                .items(items)
                .build();
    }
}