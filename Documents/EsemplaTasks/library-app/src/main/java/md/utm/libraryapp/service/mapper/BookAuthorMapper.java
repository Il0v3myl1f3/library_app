package md.utm.libraryapp.service.mapper;

import md.utm.libraryapp.domain.Author;
import md.utm.libraryapp.domain.Book;
import md.utm.libraryapp.domain.BookAuthor;
import md.utm.libraryapp.service.dto.AuthorDTO;
import md.utm.libraryapp.service.dto.BookAuthorDTO;
import md.utm.libraryapp.service.dto.BookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookAuthor} and its DTO {@link BookAuthorDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookAuthorMapper extends EntityMapper<BookAuthorDTO, BookAuthor> {
    @Mapping(target = "author", source = "author", qualifiedByName = "authorFirstName")
    @Mapping(target = "book", source = "book", qualifiedByName = "bookIsbn")
    BookAuthorDTO toDto(BookAuthor s);

    @Named("authorFirstName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    AuthorDTO toDtoAuthorFirstName(Author author);

    @Named("bookIsbn")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isbn", source = "isbn")
    BookDTO toDtoBookIsbn(Book book);
}
