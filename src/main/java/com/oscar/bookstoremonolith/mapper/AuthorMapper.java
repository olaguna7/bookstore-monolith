package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookSummaryMapper.class})
public interface AuthorMapper {

    AuthorDTO toDto(Author author);

    List<AuthorDTO> toDtoList(List<Author> authors);

}
