package com.oscar.bookstoremonolith.dto;

import java.util.List;

public class BookDTO {
        String title;
        String isbn;
        String description;
        double price;
        OrderDTO order;
        List<AuthorDTO> authors;
}
