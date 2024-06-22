package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;


@Data
@Builder
public class User {
    private long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}
