package ru.practicum.shareit.booking.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	private long id;
	@NotNull
	private long itemId;
	private ItemDto item;
	@NotNull
	@FutureOrPresent
	private LocalDateTime start;
	@NotNull
	@Future
	private LocalDateTime end;
	private UserDto booker;
	private long bookerId;
	private BookingStatus status;
}