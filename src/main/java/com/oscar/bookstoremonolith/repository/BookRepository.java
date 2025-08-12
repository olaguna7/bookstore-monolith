package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBookIdIn(List<Long> bookIds);

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findAllByTitleContaining(String title, Pageable pageable);

    Page<Book> findByAuthors_AuthorId(Long authorId, Pageable pageable);
}
