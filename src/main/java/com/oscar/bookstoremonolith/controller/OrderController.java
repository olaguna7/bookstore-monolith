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

    // ---------- GETs --------------

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

    // ---------- POSTs --------------

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderCreateDTO orderDTO) {
        OrderDTO orderCreated = orderService.createOrder(orderDTO);
        ApiResponse<OrderDTO> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Order created",
                orderCreated,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // ---------- PUTs --------------

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderCreateDTO orderDTO) {
        OrderDTO orderUpdated = orderService.updateOrder(orderId, orderDTO);
        ApiResponse<OrderDTO> apiResponse = ApiResponseUtils.success("Order sucessfully updated", orderUpdated);
        return ResponseEntity.ok(apiResponse);
    }

    // ---------- DELETEs --------------

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Order successfully deleted",
                null,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

}
