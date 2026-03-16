package md.utm.libraryapp.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import md.utm.libraryapp.repository.BorrowedBookRepository;
import md.utm.libraryapp.service.BorrowedBookQueryService;
import md.utm.libraryapp.service.BorrowedBookService;
import md.utm.libraryapp.service.criteria.BorrowedBookCriteria;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import md.utm.libraryapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link md.utm.libraryapp.domain.BorrowedBook}.
 */
@RestController
@RequestMapping("/api/borrowed-books")
public class BorrowedBookResource {

    private static final Logger LOG = LoggerFactory.getLogger(BorrowedBookResource.class);

    private static final String ENTITY_NAME = "borrowedBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BorrowedBookService borrowedBookService;

    private final BorrowedBookRepository borrowedBookRepository;

    private final BorrowedBookQueryService borrowedBookQueryService;

    public BorrowedBookResource(
        BorrowedBookService borrowedBookService,
        BorrowedBookRepository borrowedBookRepository,
        BorrowedBookQueryService borrowedBookQueryService
    ) {
        this.borrowedBookService = borrowedBookService;
        this.borrowedBookRepository = borrowedBookRepository;
        this.borrowedBookQueryService = borrowedBookQueryService;
    }

    /**
     * {@code POST  /borrowed-books} : Create a new borrowedBook.
     *
     * @param borrowedBookDTO the borrowedBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new borrowedBookDTO, or with status {@code 400 (Bad Request)} if the borrowedBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BorrowedBookDTO> createBorrowedBook(@Valid @RequestBody BorrowedBookDTO borrowedBookDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save BorrowedBook : {}", borrowedBookDTO);
        if (borrowedBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new borrowedBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        borrowedBookDTO = borrowedBookService.save(borrowedBookDTO);
        return ResponseEntity.created(new URI("/api/borrowed-books/" + borrowedBookDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, borrowedBookDTO.getId().toString()))
            .body(borrowedBookDTO);
    }

    /**
     * {@code PUT  /borrowed-books/:id} : Updates an existing borrowedBook.
     *
     * @param id the id of the borrowedBookDTO to save.
     * @param borrowedBookDTO the borrowedBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedBookDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the borrowedBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BorrowedBookDTO> updateBorrowedBook(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BorrowedBookDTO borrowedBookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BorrowedBook : {}, {}", id, borrowedBookDTO);
        if (borrowedBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        borrowedBookDTO = borrowedBookService.update(borrowedBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedBookDTO.getId().toString()))
            .body(borrowedBookDTO);
    }

    /**
     * {@code PATCH  /borrowed-books/:id} : Partial updates given fields of an existing borrowedBook, field will ignore if it is null
     *
     * @param id the id of the borrowedBookDTO to save.
     * @param borrowedBookDTO the borrowedBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedBookDTO,
     * or with status {@code 400 (Bad Request)} if the borrowedBookDTO is not valid,
     * or with status {@code 404 (Not Found)} if the borrowedBookDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the borrowedBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BorrowedBookDTO> partialUpdateBorrowedBook(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BorrowedBookDTO borrowedBookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BorrowedBook partially : {}, {}", id, borrowedBookDTO);
        if (borrowedBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BorrowedBookDTO> result = borrowedBookService.partialUpdate(borrowedBookDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedBookDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /borrowed-books} : get all the borrowedBooks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borrowedBooks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BorrowedBookDTO>> getAllBorrowedBooks(
        BorrowedBookCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get BorrowedBooks by criteria: {}", criteria);

        Page<BorrowedBookDTO> page = borrowedBookQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /borrowed-books/count} : count all the borrowedBooks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBorrowedBooks(BorrowedBookCriteria criteria) {
        LOG.debug("REST request to count BorrowedBooks by criteria: {}", criteria);
        return ResponseEntity.ok().body(borrowedBookQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /borrowed-books/:id} : get the "id" borrowedBook.
     *
     * @param id the id of the borrowedBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the borrowedBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BorrowedBookDTO> getBorrowedBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BorrowedBook : {}", id);
        Optional<BorrowedBookDTO> borrowedBookDTO = borrowedBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(borrowedBookDTO);
    }

    /**
     * {@code DELETE  /borrowed-books/:id} : delete the "id" borrowedBook.
     *
     * @param id the id of the borrowedBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BorrowedBook : {}", id);
        borrowedBookService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
