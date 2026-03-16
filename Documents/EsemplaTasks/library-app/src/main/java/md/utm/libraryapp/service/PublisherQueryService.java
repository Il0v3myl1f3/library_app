package md.utm.libraryapp.service;

import md.utm.libraryapp.domain.*; // for static metamodels
import md.utm.libraryapp.domain.Publisher;
import md.utm.libraryapp.repository.PublisherRepository;
import md.utm.libraryapp.service.criteria.PublisherCriteria;
import md.utm.libraryapp.service.dto.PublisherDTO;
import md.utm.libraryapp.service.mapper.PublisherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Publisher} entities in the database.
 * The main input is a {@link PublisherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PublisherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PublisherQueryService extends QueryService<Publisher> {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherQueryService.class);

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    public PublisherQueryService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    /**
     * Return a {@link Page} of {@link PublisherDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PublisherDTO> findByCriteria(PublisherCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Publisher> specification = createSpecification(criteria);
        return publisherRepository.findAll(specification, page).map(publisherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PublisherCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Publisher> specification = createSpecification(criteria);
        return publisherRepository.count(specification);
    }

    /**
     * Function to convert {@link PublisherCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Publisher> createSpecification(PublisherCriteria criteria) {
        Specification<Publisher> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Publisher_.id),
                buildStringSpecification(criteria.getName(), Publisher_.name)
            );
        }
        return specification;
    }
}
