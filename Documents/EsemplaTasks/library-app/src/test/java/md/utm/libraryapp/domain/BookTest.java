package md.utm.libraryapp.domain;

import static md.utm.libraryapp.domain.BookTestSamples.*;
import static md.utm.libraryapp.domain.PublisherTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import md.utm.libraryapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = getBookSample1();
        Book book2 = new Book();
        assertThat(book1).isNotEqualTo(book2);

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);

        book2 = getBookSample2();
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void publisherTest() {
        Book book = getBookRandomSampleGenerator();
        Publisher publisherBack = getPublisherRandomSampleGenerator();

        book.setPublisher(publisherBack);
        assertThat(book.getPublisher()).isEqualTo(publisherBack);

        book.publisher(null);
        assertThat(book.getPublisher()).isNull();
    }
}
