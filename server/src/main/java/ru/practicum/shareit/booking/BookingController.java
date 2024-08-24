package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookingDto> addBooking(@RequestBody @Valid BookingDto booking,
                                                 @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        log.info("Получен запрос POST /bookings/.");
        return new ResponseEntity<>(bookingService.addBooking(booking, userId), HttpStatus.OK);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> approveBooking(@PathVariable long bookingId,
                                                     @RequestParam boolean approved,
                                                     @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        log.info("Получен запрос PATCH /bookings/{bookingId}");
        return new ResponseEntity<>(bookingService.approveBooking(bookingId, approved, userId), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable long bookingId,
                                                     @RequestHeader(value = SHARER_USER_ID_HEADER) long userId) {
        log.info("Получен запрос GET /bookings/{bookingId}");
        return new ResponseEntity<>(bookingService.getBooking(bookingId, userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllByUser(@RequestParam(defaultValue = "ALL") String state,
                                                         @RequestHeader(value = SHARER_USER_ID_HEADER) Long userId,
                                                         @RequestParam(name = "from", defaultValue = "0") int from,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Получен запрос GET /bookings/");
        return new ResponseEntity<>(bookingService.getAllByUser(userId, BookingState.convert(state), from, size),
                HttpStatus.OK);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getBookingsByItems(@RequestParam(defaultValue = "ALL") String state,
                                                               @RequestHeader(value = SHARER_USER_ID_HEADER) long userId,
                                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Получен запрос GET /bookings/owner");
        return new ResponseEntity<>(bookingService.getBookingsByItems(userId, BookingState.convert(state), from, size),
                HttpStatus.OK);
    }
}