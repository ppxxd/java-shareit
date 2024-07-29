package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Booking.BookingState;
import ru.practicum.shareit.booking.model.Booking.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ObjectAccessException;
import ru.practicum.shareit.exception.ObjectCreateException;
import ru.practicum.shareit.exception.ObjectExistException;
import ru.practicum.shareit.exception.ObjectNotAvailableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDto addBooking(BookingDto bookingDto, long userId) {
        checkDate(bookingDto);
        Item item = getItemById(bookingDto.getItemId());

        if (!item.isAvailable()) {
            throw new ObjectNotAvailableException("Вещь не доступна!");
        }
        User user = getUserById(userId);
        if (item.getOwner().equals(user)) {
            throw new ObjectAccessException("Вы не можете забронировать свои вещи!");
        }

        return BookingMapper.bookingToDto(repository.save(BookingMapper.toBooking(bookingDto, item, user)));
    }

    @Override
    @Transactional
    public BookingDto approveBooking(long bookingId, boolean status, long userId) {
        BookingDto booking = BookingMapper.bookingToDto(getBookingById(bookingId));
        Item item = getItemById(booking.getItemId());

        if (item.getOwner().getId() != userId)
            throw new ObjectAccessException("У вас нет доступа к данному бронированию!");
        if (booking.getStatus().equals(BookingStatus.APPROVED) || booking.getStatus().equals(BookingStatus.REJECTED))
            throw new ObjectNotAvailableException("Бронь уже " + booking.getStatus());

        booking.setStatus(status ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.bookingToDto(repository.save(BookingMapper.toBooking(booking, item, booking.getBooker())));
    }

    @Override
    public BookingDto getBooking(long bookingId, long userId) {
        Booking booking = getBookingById(bookingId);
        User user = getUserById(userId);

        if (booking.getItem().getOwner().getId() != user.getId() && booking.getBooker().getId() != user.getId())
            throw new ObjectAccessException("У вас нет доступа к данному бронированию!");

        return BookingMapper.bookingToDto(booking);
    }

    @Override
    public List<BookingDto> getAllByUser(long userId, BookingState bookingState, int from, int size) {
        User user = getUserById(userId);

        return repository.findAllByBookerIdOrderByStartDesc(user.getId(), createPageable(from, size)).stream()
                .filter(o -> filterByState(o, bookingState))
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .map(BookingMapper::bookingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getBookingsByItems(long userId, BookingState bookingState, int from, int size) {
        User user = getUserById(userId);

        return repository.findAllByBookerIdOrderByStartDesc(user.getId(), createPageable(from, size)).stream()
                .filter(o -> filterByState(o, bookingState))
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .map(BookingMapper::bookingToDto)
                .collect(Collectors.toList());
    }

    private boolean filterByState(Booking booking, BookingState bookingState) {
        final LocalDateTime currentTime = LocalDateTime.now();

        switch (bookingState) {
            case REJECTED:
                return booking.getStatus().equals(BookingStatus.REJECTED);
            case PAST:
                return booking.getEnd().isBefore(currentTime);
            case CURRENT:
                return booking.getStart().isBefore(currentTime) &&
                        booking.getEnd().isAfter(currentTime);
            case FUTURE:
                return booking.getStart().isAfter(currentTime) ||
                        booking.getStart().equals(currentTime);
            case WAITING:
                return booking.getStatus().equals(BookingStatus.WAITING);
            default:
                return true;
        }
    }

    private Booking getBookingById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectExistException("Бронь не существует!"));
    }

    private User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectExistException("Пользователь не существует!"));
    }

    private Item getItemById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ObjectExistException("Вещь не существует!"));
    }

    private void checkDate(BookingDto booking) {
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().equals(booking.getEnd()))
            throw new ObjectCreateException("Время окончания бронирования не может быть после/равным началу!");
    }

    private Pageable createPageable(int from, int size) {
        int page = from == 0 ? 0 : from / size;
        return PageRequest.of(page, size);
    }
}