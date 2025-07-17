package com.oscar.bookstoremonolith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookSummaryDTO {
    private Long bookId;
    private String title;
    private String isbn;
    private String description;
    private double price;
}
