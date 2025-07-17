package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.BookDTO;
import com.oscar.bookstoremonolith.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    BookDTO toDto(Book book);

}