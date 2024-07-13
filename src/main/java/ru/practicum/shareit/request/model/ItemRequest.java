package ru.practicum.shareit.request.model;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Builder
public class ItemRequest {
    private long id;
    @NotNull
    private String description;
    @NotNull
    private User requestor;
    @NotNull
    private LocalDate created;
}
