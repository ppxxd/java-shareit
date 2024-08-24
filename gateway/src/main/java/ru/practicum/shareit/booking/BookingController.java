package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private static final String USER_ID_HEADER = "X-Sharer-User-Id";
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> addBooking(@RequestBody @Validated BookingDto booking,
											 @RequestHeader(value = USER_ID_HEADER) long userId) {
		log.info("Получен запрос POST /bookings/.");
		log.info("Booking: {}, userId={}", booking, userId);
		return bookingClient.addBooking(booking, userId);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> approveBooking(@PathVariable long bookingId,
												 @RequestParam boolean approved,
												 @RequestHeader(value = USER_ID_HEADER) long userId) {
		log.info("Получен запрос PATCH /bookings/{bookingId}");
		return bookingClient.approveBooking(bookingId, approved, userId);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@RequestHeader(USER_ID_HEADER) long userId,
											 @PathVariable Long bookingId) {
		log.info("Получен запрос GET /bookings/{bookingId}");
		log.info("Booking {}, userId={}", bookingId, userId);
		return bookingClient.getBookingById(userId, bookingId);
	}

	@GetMapping
	public ResponseEntity<Object> getBookingsByUser(@RequestHeader(USER_ID_HEADER) long userId,
													@RequestParam(defaultValue = "ALL") BookingState state,
													@RequestParam(name = "from", defaultValue = "0") int from,
													@RequestParam(name = "size", defaultValue = "10") int size) {
		log.info("Получен запрос GET /bookings");
		return bookingClient.getBookingsByUser(userId, state, from, size);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingByItems(@RequestParam(defaultValue = "ALL") BookingState state,
													@RequestHeader(value = USER_ID_HEADER) long userId,
													@RequestParam(name = "from", defaultValue = "0") int from,
													@RequestParam(name = "size", defaultValue = "10") int size) {
		log.info("Получен запрос GET /bookings/owner");
		return bookingClient.getBookingByItems(state, userId, from, size);
	}
}