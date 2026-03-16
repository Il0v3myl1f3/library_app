package md.utm.libraryapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link md.utm.libraryapp.domain.BorrowedBook} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BorrowedBookDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 13)
    private String bookIsbn;

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private ClientDTO client;

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

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
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
        if (!(o instanceof BorrowedBookDTO)) {
            return false;
        }

        BorrowedBookDTO borrowedBookDTO = (BorrowedBookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, borrowedBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedBookDTO{" +
            "id=" + getId() +
            ", bookIsbn='" + getBookIsbn() + "'" +
            ", borrowDate='" + getBorrowDate() + "'" +
            ", client=" + getClient() +
            ", book=" + getBook() +
            "}";
    }
}
