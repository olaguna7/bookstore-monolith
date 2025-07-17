package com.oscar.bookstoremonolith.mapper;

import com.oscar.bookstoremonolith.dto.AuthorCreateDTO;
import com.oscar.bookstoremonolith.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorCreateMapper {

    AuthorCreateDTO toDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorCreateDTO authorCreateDTO);

}
