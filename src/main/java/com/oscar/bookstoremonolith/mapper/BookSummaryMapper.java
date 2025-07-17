package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.BookSummaryDTO;
import com.oscar.bookstoremonolith.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookSummaryMapper {

    BookSummaryDTO toDto(Book book);

}
