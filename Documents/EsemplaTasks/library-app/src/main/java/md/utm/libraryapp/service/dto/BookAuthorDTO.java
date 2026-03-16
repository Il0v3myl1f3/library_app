package md.utm.libraryapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link md.utm.libraryapp.domain.BookAuthor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookAuthorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 13)
    private String bookIsbn;

    @NotNull
    private AuthorDTO author;

    @NotNull
    private BookDTO book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookAuthorDTO)) {
            return false;
        }

        BookAuthorDTO bookAuthorDTO = (BookAuthorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookAuthorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookAuthorDTO{" +
            "id=" + getId() +
            ", bookIsbn='" + getBookIsbn() + "'" +
            ", author=" + getAuthor() +
            ", book=" + getBook() +
            "}";
    }
}
