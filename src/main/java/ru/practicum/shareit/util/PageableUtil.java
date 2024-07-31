package ru.practicum.shareit.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {

    public static Pageable makePageable(int from, int size) {
        int page = from == 0 ? 0 : from / size;
        return PageRequest.of(page, size);
    }
}
