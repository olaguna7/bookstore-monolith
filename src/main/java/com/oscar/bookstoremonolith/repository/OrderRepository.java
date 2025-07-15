package com.oscar.bookstoremonolith.repository;

import com.oscar.bookstoremonolith.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
