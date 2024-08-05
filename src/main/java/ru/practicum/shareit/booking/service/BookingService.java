package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking.BookingState;

import java.util.List;

public interface BookingService {
    BookingDto addBooking(BookingDto booking, long userId);

    BookingDto approveBooking(long bookingId, boolean status, long userId);

    BookingDto getBooking(long bookingId, long userId);

    List<BookingDto> getAllByUser(long userId, BookingState status, int from, int size);

    List<BookingDto> getBookingsByItems(long userId, BookingState status, int from, int size);
}