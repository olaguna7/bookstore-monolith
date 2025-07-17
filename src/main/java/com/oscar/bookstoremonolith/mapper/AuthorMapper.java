package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.AuthorDTO;
import com.oscar.bookstoremonolith.entity.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    AuthorDTO toDto(Author author);

}
