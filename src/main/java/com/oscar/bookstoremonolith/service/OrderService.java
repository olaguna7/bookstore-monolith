package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.OrderCreateDTO;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.entity.Book;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.entity.User;
import com.oscar.bookstoremonolith.mapper.OrderMapper;
import com.oscar.bookstoremonolith.repository.BookRepository;
import com.oscar.bookstoremonolith.repository.OrderRepository;
import com.oscar.bookstoremonolith.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    public OrderDTO findById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return orderMapper.toDto(order);
    }

    public Page<OrderDTO> findOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findAllByUser_UserId(userId, pageable).map(orderMapper::toDto);
    }

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
        User user = userRepository.findById(orderCreateDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        List<Book> books = bookRepository.findAllByBookIdIn(orderCreateDTO.getBooksIds());
        Order order = new Order();
        order.setAddress(orderCreateDTO.getAddress());
        order.setUser(user);
        order.setBooks(books);
        books.forEach(book -> book.setOrder(order));
        OrderDTO orderDTO = orderMapper.toDto(orderRepository.save(order));
        bookRepository.saveAll(books);
        orderDTO.setTotal(calculatePriceOrder(order));
        return orderDTO;
    }

    private double calculatePriceOrder(Order order) {
        return order.getBooks().stream()
                .map(Book::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}
