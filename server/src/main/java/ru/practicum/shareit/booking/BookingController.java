package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private static final String SHARER_USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto addBooking(@RequestBody @Valid BookingDto booking,
                                                 @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        return bookingService.addBooking(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@PathVariable long bookingId,
                                                     @RequestParam boolean approved,
                                                     @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        return bookingService.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable long bookingId,
                                                     @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getAllByUser(@RequestParam(defaultValue = "ALL") String state,
                                                         @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId,
                                                         @RequestParam(name = "from", defaultValue = "0") int from,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        return bookingService.getAllByUser(userId, BookingState.convert(state), from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsByItems(@RequestParam(defaultValue = "ALL") String state,
                                                               @RequestHeader(value = SHARER_USER_ID_HEADER) long userId,
                                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return bookingService.getBookingsByItems(userId, BookingState.convert(state), from, size);
    }
}