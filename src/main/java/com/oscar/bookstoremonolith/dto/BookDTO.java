package com.oscar.bookstoremonolith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookDTO {
        private Long bookId;
        private String title;
        private String isbn;
        private String description;
        private double price;
        private OrderDTO order;
        private List<AuthorDTO> authors;
}
