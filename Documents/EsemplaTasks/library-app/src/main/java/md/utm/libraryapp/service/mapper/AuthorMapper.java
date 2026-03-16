package md.utm.libraryapp.service.mapper;

import md.utm.libraryapp.domain.Author;
import md.utm.libraryapp.service.dto.AuthorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {}
