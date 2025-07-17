package com.oscar.bookstoremonolith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookCreateDTO {
    private String title;
    private String isbn;
    private String description;
    private double price;
    private List<Long> authorsIds;
}
