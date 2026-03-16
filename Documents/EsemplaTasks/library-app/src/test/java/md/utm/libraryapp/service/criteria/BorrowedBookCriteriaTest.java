package md.utm.libraryapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BorrowedBookCriteriaTest {

    @Test
    void newBorrowedBookCriteriaHasAllFiltersNullTest() {
        var borrowedBookCriteria = new BorrowedBookCriteria();
        assertThat(borrowedBookCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void borrowedBookCriteriaFluentMethodsCreatesFiltersTest() {
        var borrowedBookCriteria = new BorrowedBookCriteria();

        setAllFilters(borrowedBookCriteria);

        assertThat(borrowedBookCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void borrowedBookCriteriaCopyCreatesNullFilterTest() {
        var borrowedBookCriteria = new BorrowedBookCriteria();
        var copy = borrowedBookCriteria.copy();

        assertThat(borrowedBookCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(borrowedBookCriteria)
        );
    }

    @Test
    void borrowedBookCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var borrowedBookCriteria = new BorrowedBookCriteria();
        setAllFilters(borrowedBookCriteria);

        var copy = borrowedBookCriteria.copy();

        assertThat(borrowedBookCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(borrowedBookCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var borrowedBookCriteria = new BorrowedBookCriteria();

        assertThat(borrowedBookCriteria).hasToString("BorrowedBookCriteria{}");
    }

    private static void setAllFilters(BorrowedBookCriteria borrowedBookCriteria) {
        borrowedBookCriteria.id();
        borrowedBookCriteria.bookIsbn();
        borrowedBookCriteria.borrowDate();
        borrowedBookCriteria.clientId();
        borrowedBookCriteria.bookId();
        borrowedBookCriteria.distinct();
    }

    private static Condition<BorrowedBookCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getBookIsbn()) &&
                condition.apply(criteria.getBorrowDate()) &&
                condition.apply(criteria.getClientId()) &&
                condition.apply(criteria.getBookId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BorrowedBookCriteria> copyFiltersAre(
        BorrowedBookCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getBookIsbn(), copy.getBookIsbn()) &&
                condition.apply(criteria.getBorrowDate(), copy.getBorrowDate()) &&
                condition.apply(criteria.getClientId(), copy.getClientId()) &&
                condition.apply(criteria.getBookId(), copy.getBookId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
