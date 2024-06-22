package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;



@Data
@Builder
public class Item {
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private boolean available;
    private User owner;
    private Long request;
}
