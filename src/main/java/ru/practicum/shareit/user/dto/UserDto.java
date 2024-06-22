package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

@Data
@Builder
public class UserDto {
    private long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}