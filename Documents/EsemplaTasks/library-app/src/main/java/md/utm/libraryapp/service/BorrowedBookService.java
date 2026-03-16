package md.utm.libraryapp.service;

import java.util.Optional;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link md.utm.libraryapp.domain.BorrowedBook}.
 */
public interface BorrowedBookService {
    /**
     * Save a borrowedBook.
     *
     * @param borrowedBookDTO the entity to save.
     * @return the persisted entity.
     */
    BorrowedBookDTO save(BorrowedBookDTO borrowedBookDTO);

    /**
     * Updates a borrowedBook.
     *
     * @param borrowedBookDTO the entity to update.
     * @return the persisted entity.
     */
    BorrowedBookDTO update(BorrowedBookDTO borrowedBookDTO);

    /**
     * Partially updates a borrowedBook.
     *
     * @param borrowedBookDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BorrowedBookDTO> partialUpdate(BorrowedBookDTO borrowedBookDTO);

    /**
     * Get all the borrowedBooks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BorrowedBookDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" borrowedBook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BorrowedBookDTO> findOne(Long id);

    /**
     * Delete the "id" borrowedBook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
