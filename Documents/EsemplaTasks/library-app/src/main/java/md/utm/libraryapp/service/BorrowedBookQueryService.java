package md.utm.libraryapp.service;

import jakarta.persistence.criteria.JoinType;
import md.utm.libraryapp.domain.*; // for static metamodels
import md.utm.libraryapp.domain.BorrowedBook;
import md.utm.libraryapp.repository.BorrowedBookRepository;
import md.utm.libraryapp.service.criteria.BorrowedBookCriteria;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import md.utm.libraryapp.service.mapper.BorrowedBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BorrowedBook} entities in the database.
 * The main input is a {@link BorrowedBookCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BorrowedBookDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BorrowedBookQueryService extends QueryService<BorrowedBook> {

    private static final Logger LOG = LoggerFactory.getLogger(BorrowedBookQueryService.class);

    private final BorrowedBookRepository borrowedBookRepository;

    private final BorrowedBookMapper borrowedBookMapper;

    public BorrowedBookQueryService(BorrowedBookRepository borrowedBookRepository, BorrowedBookMapper borrowedBookMapper) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.borrowedBookMapper = borrowedBookMapper;
    }

    /**
     * Return a {@link Page} of {@link BorrowedBookDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BorrowedBookDTO> findByCriteria(BorrowedBookCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BorrowedBook> specification = createSpecification(criteria);
        return borrowedBookRepository.findAll(specification, page).map(borrowedBookMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BorrowedBookCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<BorrowedBook> specification = createSpecification(criteria);
        return borrowedBookRepository.count(specification);
    }

    /**
     * Function to convert {@link BorrowedBookCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BorrowedBook> createSpecification(BorrowedBookCriteria criteria) {
        Specification<BorrowedBook> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), BorrowedBook_.id),
                buildStringSpecification(criteria.getBookIsbn(), BorrowedBook_.bookIsbn),
                buildRangeSpecification(criteria.getBorrowDate(), BorrowedBook_.borrowDate),
                buildSpecification(criteria.getClientId(), root -> root.join(BorrowedBook_.client, JoinType.LEFT).get(Client_.id)),
                buildSpecification(criteria.getBookId(), root -> root.join(BorrowedBook_.book, JoinType.LEFT).get(Book_.id))
            );
        }
        return specification;
    }
}
