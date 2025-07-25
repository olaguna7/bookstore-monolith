package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.AuthorCreateDTO;
import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.service.AuthorService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ApiResponse<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors =  authorService.findAll();
        return ApiResponseUtils.success("Authors found", authors);
    }

    @GetMapping("/{authorId}")
    public ApiResponse<AuthorDTO> getUserById(@PathVariable("authorId") Long authorId) {
        AuthorDTO author = authorService.findById(authorId);
        return ApiResponseUtils.success("Author found", author);
    }

    @PostMapping
    public ApiResponse<AuthorDTO> createAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorDTO author = authorService.createAuthor(authorCreateDTO);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Author created",
                author,
                LocalDateTime.now()
        );
    }

}
