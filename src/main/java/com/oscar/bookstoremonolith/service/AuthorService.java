package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.AuthorCreateDTO;
import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.entity.Author;
import com.oscar.bookstoremonolith.entity.Book;
import com.oscar.bookstoremonolith.mapper.AuthorCreateMapper;
import com.oscar.bookstoremonolith.mapper.AuthorMapper;
import com.oscar.bookstoremonolith.repository.AuthorRepository;
import com.oscar.bookstoremonolith.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final AuthorCreateMapper authorCreateMapper;

    public Page<AuthorDTO> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toDto);
    }

    public AuthorDTO findById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(EntityNotFoundException::new);
        return authorMapper.toDto(author);
    }

    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        List<Book> books = bookRepository.findAllByBookIdIn(authorCreateDTO.getBooksIds());
        Author author = authorCreateMapper.toEntity(authorCreateDTO);
        author.setBooks(books);
        return authorMapper.toDto(authorRepository.save(author));
    }

}
