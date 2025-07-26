package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.BookCreateDTO;
import com.oscar.bookstoremonolith.dto.BookDTO;
import com.oscar.bookstoremonolith.entity.Author;
import com.oscar.bookstoremonolith.entity.Book;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.exception.DuplicateEntityException;
import com.oscar.bookstoremonolith.mapper.BookCreateMapper;
import com.oscar.bookstoremonolith.mapper.BookMapper;
import com.oscar.bookstoremonolith.repository.AuthorRepository;
import com.oscar.bookstoremonolith.repository.BookRepository;
import com.oscar.bookstoremonolith.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final BookCreateMapper bookCreateMapper;

    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDto);
    }

    public BookDTO findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public BookDTO findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(EntityNotFoundException::new);
        return bookMapper.toDto(book);
    }

    public Page<BookDTO> findByTitleContaining(String title, Pageable pageable) {
        return bookRepository.findAllByTitleContaining(title, pageable).map(bookMapper::toDto);
    }

    public Page<BookDTO> findAllByAuthor(Long authorId, Pageable pageable) {
        return bookRepository.findByAuthors_AuthorId(authorId, pageable).map(bookMapper::toDto);
    }

    public BookDTO createBook(BookCreateDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateEntityException("Book", "isbn", bookDTO.getIsbn());
        }

        Book book = bookCreateMapper.toEntity(bookDTO);
        List<Author> authors = authorRepository.findAllByAuthorIdIn(bookDTO.getAuthorsIds());
        book.setAuthors(authors);
        authors.forEach(author -> author.getBooks().add(book));
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO updateBook(Long bookId, BookCreateDTO bookDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);

        if (bookRepository.existsByIsbn(bookDTO.getIsbn()) && !bookDTO.getIsbn().equals(book.getIsbn())) {
            throw new DuplicateEntityException("Book", "isbn", bookDTO.getIsbn());
        }

        List<Author> authors = authorRepository.findAllByAuthorIdIn(bookDTO.getAuthorsIds());
        book.setAuthors(authors);
        authors.forEach(author -> author.getBooks().add(book));
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException();
        }

        bookRepository.deleteById(bookId);
    }

}
