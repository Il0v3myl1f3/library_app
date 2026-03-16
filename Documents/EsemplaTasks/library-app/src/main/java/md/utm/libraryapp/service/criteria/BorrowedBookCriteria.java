package md.utm.libraryapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link md.utm.libraryapp.domain.BorrowedBook} entity. This class is used
 * in {@link md.utm.libraryapp.web.rest.BorrowedBookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /borrowed-books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BorrowedBookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bookIsbn;

    private LocalDateFilter borrowDate;

    private LongFilter clientId;

    private LongFilter bookId;

    private Boolean distinct;

    public BorrowedBookCriteria() {}

    public BorrowedBookCriteria(BorrowedBookCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.bookIsbn = other.optionalBookIsbn().map(StringFilter::copy).orElse(null);
        this.borrowDate = other.optionalBorrowDate().map(LocalDateFilter::copy).orElse(null);
        this.clientId = other.optionalClientId().map(LongFilter::copy).orElse(null);
        this.bookId = other.optionalBookId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BorrowedBookCriteria copy() {
        return new BorrowedBookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBookIsbn() {
        return bookIsbn;
    }

    public Optional<StringFilter> optionalBookIsbn() {
        return Optional.ofNullable(bookIsbn);
    }

    public StringFilter bookIsbn() {
        if (bookIsbn == null) {
            setBookIsbn(new StringFilter());
        }
        return bookIsbn;
    }

    public void setBookIsbn(StringFilter bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public LocalDateFilter getBorrowDate() {
        return borrowDate;
    }

    public Optional<LocalDateFilter> optionalBorrowDate() {
        return Optional.ofNullable(borrowDate);
    }

    public LocalDateFilter borrowDate() {
        if (borrowDate == null) {
            setBorrowDate(new LocalDateFilter());
        }
        return borrowDate;
    }

    public void setBorrowDate(LocalDateFilter borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public Optional<LongFilter> optionalClientId() {
        return Optional.ofNullable(clientId);
    }

    public LongFilter clientId() {
        if (clientId == null) {
            setClientId(new LongFilter());
        }
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getBookId() {
        return bookId;
    }

    public Optional<LongFilter> optionalBookId() {
        return Optional.ofNullable(bookId);
    }

    public LongFilter bookId() {
        if (bookId == null) {
            setBookId(new LongFilter());
        }
        return bookId;
    }

    public void setBookId(LongFilter bookId) {
        this.bookId = bookId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BorrowedBookCriteria that = (BorrowedBookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bookIsbn, that.bookIsbn) &&
            Objects.equals(borrowDate, that.borrowDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(bookId, that.bookId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookIsbn, borrowDate, clientId, bookId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedBookCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalBookIsbn().map(f -> "bookIsbn=" + f + ", ").orElse("") +
            optionalBorrowDate().map(f -> "borrowDate=" + f + ", ").orElse("") +
            optionalClientId().map(f -> "clientId=" + f + ", ").orElse("") +
            optionalBookId().map(f -> "bookId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
