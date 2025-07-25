package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.BookDTO;
import com.oscar.bookstoremonolith.entity.Book;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", uses = {AuthorSummaryMapper.class})
public interface BookMapper {

    BookDTO toDto(Book book);

    List<BookDTO> toDtoList(List<Book> books);

}