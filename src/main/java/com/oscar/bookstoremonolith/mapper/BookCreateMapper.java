package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.BookCreateDTO;
import com.oscar.bookstoremonolith.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookCreateMapper {

    BookCreateDTO toDto(Book book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "order", ignore = true)
    Book toEntity(BookCreateDTO bookCreateDTO);
}
