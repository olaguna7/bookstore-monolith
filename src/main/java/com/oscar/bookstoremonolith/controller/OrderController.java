package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.OrderCreateDTO;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.service.OrderService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.findAll();
        return ApiResponseUtils.success("Orders found", orders);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> getById(@PathVariable("orderId") Long orderId) {
        OrderDTO order = orderService.findById(orderId);
        return ApiResponseUtils.success("Order found", order);
    }

    @PostMapping
    public ApiResponse<OrderDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        OrderDTO orderCreated = orderService.createOrder(orderCreateDTO);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Order created",
                orderCreated,
                LocalDateTime.now()
        );
    }

}
