package md.utm.libraryapp.repository;

import java.util.List;
import java.util.Optional;
import md.utm.libraryapp.domain.BookAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BookAuthor entity.
 */
@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    default Optional<BookAuthor> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BookAuthor> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BookAuthor> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bookAuthor from BookAuthor bookAuthor left join fetch bookAuthor.author left join fetch bookAuthor.book",
        countQuery = "select count(bookAuthor) from BookAuthor bookAuthor"
    )
    Page<BookAuthor> findAllWithToOneRelationships(Pageable pageable);

    @Query("select bookAuthor from BookAuthor bookAuthor left join fetch bookAuthor.author left join fetch bookAuthor.book")
    List<BookAuthor> findAllWithToOneRelationships();

    @Query(
        "select bookAuthor from BookAuthor bookAuthor left join fetch bookAuthor.author left join fetch bookAuthor.book where bookAuthor.id =:id"
    )
    Optional<BookAuthor> findOneWithToOneRelationships(@Param("id") Long id);
}
