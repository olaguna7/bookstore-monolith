package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.AuthorSummaryDTO;
import com.oscar.bookstoremonolith.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorSummaryMapper {

    AuthorSummaryDTO toDto(Author author);

}
