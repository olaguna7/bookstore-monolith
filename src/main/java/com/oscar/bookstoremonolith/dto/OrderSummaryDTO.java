package com.oscar.bookstoremonolith.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderSummaryDTO {
    private Long orderId;
    private LocalDateTime date;
    private double total;
}
