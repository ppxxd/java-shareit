package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.exception.InvalidDataException;

public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState convert(String source) {
        try {
            return BookingState.valueOf(source);
        } catch (Exception e) {
            String message = String.format("Unknown state: %S", source);
            throw new InvalidDataException(message);
        }
    }
}