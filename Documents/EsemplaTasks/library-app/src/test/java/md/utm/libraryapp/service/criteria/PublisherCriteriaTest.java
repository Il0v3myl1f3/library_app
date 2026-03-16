package md.utm.libraryapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PublisherCriteriaTest {

    @Test
    void newPublisherCriteriaHasAllFiltersNullTest() {
        var publisherCriteria = new PublisherCriteria();
        assertThat(publisherCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void publisherCriteriaFluentMethodsCreatesFiltersTest() {
        var publisherCriteria = new PublisherCriteria();

        setAllFilters(publisherCriteria);

        assertThat(publisherCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void publisherCriteriaCopyCreatesNullFilterTest() {
        var publisherCriteria = new PublisherCriteria();
        var copy = publisherCriteria.copy();

        assertThat(publisherCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(publisherCriteria)
        );
    }

    @Test
    void publisherCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var publisherCriteria = new PublisherCriteria();
        setAllFilters(publisherCriteria);

        var copy = publisherCriteria.copy();

        assertThat(publisherCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(publisherCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var publisherCriteria = new PublisherCriteria();

        assertThat(publisherCriteria).hasToString("PublisherCriteria{}");
    }

    private static void setAllFilters(PublisherCriteria publisherCriteria) {
        publisherCriteria.id();
        publisherCriteria.name();
        publisherCriteria.distinct();
    }

    private static Condition<PublisherCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria -> condition.apply(criteria.getId()) && condition.apply(criteria.getName()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PublisherCriteria> copyFiltersAre(PublisherCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
