package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.service.BookService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

}
