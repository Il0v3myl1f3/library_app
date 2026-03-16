package md.utm.libraryapp.service.mapper;

import static md.utm.libraryapp.domain.BookAuthorAsserts.*;
import static md.utm.libraryapp.domain.BookAuthorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookAuthorMapperTest {

    private BookAuthorMapper bookAuthorMapper;

    @BeforeEach
    void setUp() {
        bookAuthorMapper = new BookAuthorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBookAuthorSample1();
        var actual = bookAuthorMapper.toEntity(bookAuthorMapper.toDto(expected));
        assertBookAuthorAllPropertiesEquals(expected, actual);
    }
}
