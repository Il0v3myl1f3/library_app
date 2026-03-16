package md.utm.libraryapp.repository;

import java.util.List;
import java.util.Optional;
import md.utm.libraryapp.domain.BorrowedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BorrowedBook entity.
 */
@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long>, JpaSpecificationExecutor<BorrowedBook> {
    default Optional<BorrowedBook> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BorrowedBook> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BorrowedBook> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.client left join fetch borrowedBook.book",
        countQuery = "select count(borrowedBook) from BorrowedBook borrowedBook"
    )
    Page<BorrowedBook> findAllWithToOneRelationships(Pageable pageable);

    @Query("select borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.client left join fetch borrowedBook.book")
    List<BorrowedBook> findAllWithToOneRelationships();

    @Query(
        "select borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.client left join fetch borrowedBook.book where borrowedBook.id =:id"
    )
    Optional<BorrowedBook> findOneWithToOneRelationships(@Param("id") Long id);
}
