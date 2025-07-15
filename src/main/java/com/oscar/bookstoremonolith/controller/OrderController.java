package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

}
