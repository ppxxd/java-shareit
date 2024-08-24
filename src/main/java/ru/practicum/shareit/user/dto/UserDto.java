package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private long id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}