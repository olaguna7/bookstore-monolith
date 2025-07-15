package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
