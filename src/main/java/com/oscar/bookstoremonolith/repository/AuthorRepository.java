package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByAuthorIdIn(Collection<Long> authorIds);

    Page<Author> findAllByNameContaining(String name, Pageable pageable);
    
}
