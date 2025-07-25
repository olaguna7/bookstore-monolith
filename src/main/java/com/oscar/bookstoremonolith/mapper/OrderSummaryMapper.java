package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.OrderSummaryDTO;
import com.oscar.bookstoremonolith.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderSummaryMapper {

    OrderSummaryDTO toDto(Order order);

}
