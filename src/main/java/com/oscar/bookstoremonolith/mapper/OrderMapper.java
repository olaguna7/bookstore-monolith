package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.userId")
    OrderDTO toDto(Order order);

    List<OrderDTO> toDtoList(List<Order> orders);

}
