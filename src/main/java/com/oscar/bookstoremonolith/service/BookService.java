package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.BookCreateDTO;
import com.oscar.bookstoremonolith.dto.BookDTO;
import com.oscar.bookstoremonolith.entity.Author;
import com.oscar.bookstoremonolith.entity.Book;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.mapper.BookCreateMapper;
import com.oscar.bookstoremonolith.mapper.BookMapper;
import com.oscar.bookstoremonolith.repository.AuthorRepository;
import com.oscar.bookstoremonolith.repository.BookRepository;
import com.oscar.bookstoremonolith.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final BookCreateMapper bookCreateMapper;

    public List<BookDTO> findAll() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    public BookDTO findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        List<Author> authors = authorRepository.findAllByAuthorIdIn(bookCreateDTO.getAuthorsIds());
        Book book = bookCreateMapper.toEntity(bookCreateDTO);
        book.setAuthors(authors);
        return bookMapper.toDto(bookRepository.save(book));
    }

}
