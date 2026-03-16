package md.utm.libraryapp.domain;

import static md.utm.libraryapp.domain.AuthorTestSamples.*;
import static md.utm.libraryapp.domain.BookAuthorTestSamples.*;
import static md.utm.libraryapp.domain.BookTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import md.utm.libraryapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookAuthorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookAuthor.class);
        BookAuthor bookAuthor1 = getBookAuthorSample1();
        BookAuthor bookAuthor2 = new BookAuthor();
        assertThat(bookAuthor1).isNotEqualTo(bookAuthor2);

        bookAuthor2.setId(bookAuthor1.getId());
        assertThat(bookAuthor1).isEqualTo(bookAuthor2);

        bookAuthor2 = getBookAuthorSample2();
        assertThat(bookAuthor1).isNotEqualTo(bookAuthor2);
    }

    @Test
    void authorTest() {
        BookAuthor bookAuthor = getBookAuthorRandomSampleGenerator();
        Author authorBack = getAuthorRandomSampleGenerator();

        bookAuthor.setAuthor(authorBack);
        assertThat(bookAuthor.getAuthor()).isEqualTo(authorBack);

        bookAuthor.author(null);
        assertThat(bookAuthor.getAuthor()).isNull();
    }

    @Test
    void bookTest() {
        BookAuthor bookAuthor = getBookAuthorRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        bookAuthor.setBook(bookBack);
        assertThat(bookAuthor.getBook()).isEqualTo(bookBack);

        bookAuthor.book(null);
        assertThat(bookAuthor.getBook()).isNull();
    }
}
