package md.utm.libraryapp.service.mapper;

import md.utm.libraryapp.domain.Book;
import md.utm.libraryapp.domain.BorrowedBook;
import md.utm.libraryapp.domain.Client;
import md.utm.libraryapp.service.dto.BookDTO;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import md.utm.libraryapp.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BorrowedBook} and its DTO {@link BorrowedBookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BorrowedBookMapper extends EntityMapper<BorrowedBookDTO, BorrowedBook> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientFirstName")
    @Mapping(target = "book", source = "book", qualifiedByName = "bookIsbn")
    BorrowedBookDTO toDto(BorrowedBook s);

    @Named("clientFirstName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    ClientDTO toDtoClientFirstName(Client client);

    @Named("bookIsbn")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isbn", source = "isbn")
    BookDTO toDtoBookIsbn(Book book);
}
