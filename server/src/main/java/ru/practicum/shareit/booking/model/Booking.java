package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exception.InvalidDataException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "bookings")
@RequiredArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Item item;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.WAITING;

    public enum BookingStatus {
        WAITING,
        APPROVED,
        REJECTED,
        CANCELED
    }

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
}
