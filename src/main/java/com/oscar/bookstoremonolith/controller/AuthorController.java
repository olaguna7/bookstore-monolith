package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.AuthorCreateDTO;
import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getUserById(@PathVariable("authorId") Long authorId) {
        AuthorDTO authorDTO = authorService.findById(authorId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorDTO authorDTO = authorService.createAuthor(authorCreateDTO);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

}
