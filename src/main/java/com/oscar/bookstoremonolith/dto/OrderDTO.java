package com.oscar.bookstoremonolith.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
        private Long orderId;
        private String address;
        private LocalDateTime date;
        private double total;
        private UserSummaryDTO user;
        private List<BookSummaryDTO> books;
}
