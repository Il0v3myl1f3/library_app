package md.utm.libraryapp.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import md.utm.libraryapp.repository.BookAuthorRepository;
import md.utm.libraryapp.service.BookAuthorService;
import md.utm.libraryapp.service.dto.BookAuthorDTO;
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
 * REST controller for managing {@link md.utm.libraryapp.domain.BookAuthor}.
 */
@RestController
@RequestMapping("/api/book-authors")
public class BookAuthorResource {

    private static final Logger LOG = LoggerFactory.getLogger(BookAuthorResource.class);

    private static final String ENTITY_NAME = "bookAuthor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookAuthorService bookAuthorService;

    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorResource(BookAuthorService bookAuthorService, BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorService = bookAuthorService;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    /**
     * {@code POST  /book-authors} : Create a new bookAuthor.
     *
     * @param bookAuthorDTO the bookAuthorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookAuthorDTO, or with status {@code 400 (Bad Request)} if the bookAuthor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BookAuthorDTO> createBookAuthor(@Valid @RequestBody BookAuthorDTO bookAuthorDTO) throws URISyntaxException {
        LOG.debug("REST request to save BookAuthor : {}", bookAuthorDTO);
        if (bookAuthorDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookAuthor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bookAuthorDTO = bookAuthorService.save(bookAuthorDTO);
        return ResponseEntity.created(new URI("/api/book-authors/" + bookAuthorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bookAuthorDTO.getId().toString()))
            .body(bookAuthorDTO);
    }

    /**
     * {@code PUT  /book-authors/:id} : Updates an existing bookAuthor.
     *
     * @param id the id of the bookAuthorDTO to save.
     * @param bookAuthorDTO the bookAuthorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookAuthorDTO,
     * or with status {@code 400 (Bad Request)} if the bookAuthorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookAuthorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookAuthorDTO> updateBookAuthor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BookAuthorDTO bookAuthorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BookAuthor : {}, {}", id, bookAuthorDTO);
        if (bookAuthorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookAuthorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookAuthorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bookAuthorDTO = bookAuthorService.update(bookAuthorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookAuthorDTO.getId().toString()))
            .body(bookAuthorDTO);
    }

    /**
     * {@code PATCH  /book-authors/:id} : Partial updates given fields of an existing bookAuthor, field will ignore if it is null
     *
     * @param id the id of the bookAuthorDTO to save.
     * @param bookAuthorDTO the bookAuthorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookAuthorDTO,
     * or with status {@code 400 (Bad Request)} if the bookAuthorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookAuthorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookAuthorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BookAuthorDTO> partialUpdateBookAuthor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BookAuthorDTO bookAuthorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BookAuthor partially : {}, {}", id, bookAuthorDTO);
        if (bookAuthorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookAuthorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookAuthorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookAuthorDTO> result = bookAuthorService.partialUpdate(bookAuthorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookAuthorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /book-authors} : get all the bookAuthors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookAuthors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BookAuthorDTO>> getAllBookAuthors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of BookAuthors");
        Page<BookAuthorDTO> page;
        if (eagerload) {
            page = bookAuthorService.findAllWithEagerRelationships(pageable);
        } else {
            page = bookAuthorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-authors/:id} : get the "id" bookAuthor.
     *
     * @param id the id of the bookAuthorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookAuthorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookAuthorDTO> getBookAuthor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BookAuthor : {}", id);
        Optional<BookAuthorDTO> bookAuthorDTO = bookAuthorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookAuthorDTO);
    }

    /**
     * {@code DELETE  /book-authors/:id} : delete the "id" bookAuthor.
     *
     * @param id the id of the bookAuthorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookAuthor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BookAuthor : {}", id);
        bookAuthorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
