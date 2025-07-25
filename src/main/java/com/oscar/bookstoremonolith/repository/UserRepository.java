package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
