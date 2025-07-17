package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.Book;
import com.oscar.bookstoremonolith.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBookIdIn(List<Long> bookIds);
}
