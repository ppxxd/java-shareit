package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.RequestItemDto;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemRequestDto {
    private long id;
    @NotBlank
    private String description;
    private Date created;
    private List<RequestItemDto> items;
}