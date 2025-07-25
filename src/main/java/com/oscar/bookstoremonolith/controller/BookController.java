package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.BookCreateDTO;
import com.oscar.bookstoremonolith.dto.BookDTO;
import com.oscar.bookstoremonolith.service.BookService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import com.oscar.bookstoremonolith.utils.PaginationUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ApiResponse<Page<BookDTO>> getAllBooks(@PageableDefault(size = 5) Pageable pageable) {
        Page<BookDTO> books = PaginationUtils.listToPage(bookService.findAll(), pageable);
        return ApiResponseUtils.success("Books found", books);
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookDTO> getBookById(@PathVariable("bookId") Long bookId) {
        BookDTO book = bookService.findById(bookId);
        return ApiResponseUtils.success("Book found", book);
    }

    @PostMapping
    public ApiResponse<BookDTO> createBook(@Valid @RequestBody BookCreateDTO bookDTO) {
        BookDTO bookCreated = bookService.createBook(bookDTO);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Book created successfully",
                bookCreated,
                LocalDateTime.now()
        );
    }

}
