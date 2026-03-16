package md.utm.libraryapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import md.utm.libraryapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookAuthorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookAuthorDTO.class);
        BookAuthorDTO bookAuthorDTO1 = new BookAuthorDTO();
        bookAuthorDTO1.setId(1L);
        BookAuthorDTO bookAuthorDTO2 = new BookAuthorDTO();
        assertThat(bookAuthorDTO1).isNotEqualTo(bookAuthorDTO2);
        bookAuthorDTO2.setId(bookAuthorDTO1.getId());
        assertThat(bookAuthorDTO1).isEqualTo(bookAuthorDTO2);
        bookAuthorDTO2.setId(2L);
        assertThat(bookAuthorDTO1).isNotEqualTo(bookAuthorDTO2);
        bookAuthorDTO1.setId(null);
        assertThat(bookAuthorDTO1).isNotEqualTo(bookAuthorDTO2);
    }
}
