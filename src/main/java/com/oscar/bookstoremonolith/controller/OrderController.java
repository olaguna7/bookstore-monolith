package com.oscar.bookstoremonolith.controller;

import com.oscar.bookstoremonolith.dto.ApiResponse;
import com.oscar.bookstoremonolith.dto.ApiResponsePaged;
import com.oscar.bookstoremonolith.dto.OrderCreateDTO;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.service.OrderService;
import com.oscar.bookstoremonolith.utils.ApiResponseUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiResponsePaged<OrderDTO>>> getAllOrders(@PageableDefault Pageable pageable) {
        Page<OrderDTO> orders = orderService.findAll(pageable);
        ApiResponsePaged<OrderDTO> responsePaged = ApiResponseUtils.successPaged(orders);
        ApiResponse<ApiResponsePaged<OrderDTO>> apiResponse = ApiResponseUtils.success("Orders found", responsePaged);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> getById(@PathVariable("orderId") Long orderId) {
        OrderDTO order = orderService.findById(orderId);
        return ApiResponseUtils.success("Order found", order);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        OrderDTO orderCreated = orderService.createOrder(orderCreateDTO);
        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Order created",
                orderCreated,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
