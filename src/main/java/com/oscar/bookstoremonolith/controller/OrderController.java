package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.OrderCreateDTO;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.entity.Order;
import com.oscar.bookstoremonolith.service.OrderService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import com.oscar.bookstoremonolith.utils.PaginationUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<Page<OrderDTO>> getAllOrders(@PageableDefault Pageable pageable) {
        Page<OrderDTO> orders = PaginationUtils.listToPage(orderService.findAll(), pageable);
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
