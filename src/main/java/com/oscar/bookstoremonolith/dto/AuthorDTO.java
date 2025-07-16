package com.oscar.bookstoremonolith.dto;

import java.util.Date;
import java.util.List;

public record AuthorDTO(
        String name,
        Date birthday,
        List<BookDTO> books
) {
}
