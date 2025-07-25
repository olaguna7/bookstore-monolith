package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.UserSummaryDTO;
import com.oscar.bookstoremonolith.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSummaryMapper {

    UserSummaryDTO toDto(User user);

}
