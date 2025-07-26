package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.*;
import com.oscar.bookstoremonolith.service.AuthorService;
import com.oscar.bookstoremonolith.service.BookService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    // ---------- GETs --------------

    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponsePaged<AuthorDTO>>> getAllAuthors(@PageableDefault(size = 20) Pageable pageable) {
        Page<AuthorDTO> authors =  authorService.findAll(pageable);
        ApiResponsePaged<AuthorDTO> responsePaged = ApiResponseUtils.successPaged(authors);
        ApiResponse<ApiResponsePaged<AuthorDTO>> apiResponse = ApiResponseUtils.success("Authors found", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getUserById(@PathVariable("authorId") Long authorId) {
        AuthorDTO author = authorService.findById(authorId);
        ApiResponse<AuthorDTO> apiResponse = ApiResponseUtils.success("Author found", author);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<ApiResponse<ApiResponsePaged<AuthorDTO>>> getUsersByName(@PathVariable String name, Pageable pageable) {
        Page<AuthorDTO> authors = authorService.findByNameContaining(name, pageable);
        ApiResponsePaged<AuthorDTO> responsePaged = ApiResponseUtils.successPaged(authors);
        ApiResponse<ApiResponsePaged<AuthorDTO>> apiResponse = ApiResponseUtils.success("Authors found with name containing " + name, responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<ApiResponse<ApiResponsePaged<BookDTO>>> getAuthorBooks(@PathVariable Long authorId, Pageable pageable) {
        Page<BookDTO> books = bookService.findAllByAuthor(authorId, pageable);
        ApiResponsePaged<BookDTO> responsePaged = ApiResponseUtils.successPaged(books);
        ApiResponse<ApiResponsePaged<BookDTO>> apiResponse = ApiResponseUtils.success("Books found by author", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    // ---------- POSTs --------------

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> createAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorDTO author = authorService.createAuthor(authorCreateDTO);
        ApiResponse<AuthorDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Author created",
                author,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // ---------- PUTs --------------

    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> updateAuthor(@PathVariable Long authorId, @Valid @RequestBody AuthorCreateDTO authorDTO) {
        AuthorDTO author = authorService.updateAuthor(authorId, authorDTO);
        ApiResponse<AuthorDTO> apiResponse = ApiResponseUtils.success("Author successfully modified", author);
        return ResponseEntity.ok(apiResponse);
    }

    // ---------- DELETEs --------------

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Author successfully deleted",
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

}
