package com.oscar.bookstoremonolith.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        String address,
        LocalDateTime date,
        double total,
        Long userId,
        List<BookDTO> books
) {
}
