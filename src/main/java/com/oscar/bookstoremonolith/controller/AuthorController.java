package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.ApiResponsePaged;
import com.oscar.bookstoremonolith.dto.AuthorCreateDTO;
import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.service.AuthorService;
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

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

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

}
