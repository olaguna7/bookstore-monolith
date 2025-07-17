package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.OrderCreateDTO;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getById(@PathVariable("orderId") Long orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        return orderService.createOrder(orderCreateDTO);
    }

}
