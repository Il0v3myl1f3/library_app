package md.utm.libraryapp.service.impl;

import java.util.Optional;
import md.utm.libraryapp.domain.Book;
import md.utm.libraryapp.domain.BorrowedBook;
import md.utm.libraryapp.repository.BookRepository;
import md.utm.libraryapp.repository.BorrowedBookRepository;
import md.utm.libraryapp.service.BorrowedBookService;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import md.utm.libraryapp.service.mapper.BorrowedBookMapper;
import md.utm.libraryapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link md.utm.libraryapp.domain.BorrowedBook}.
 */
@Service
@Transactional
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private static final Logger LOG = LoggerFactory.getLogger(BorrowedBookServiceImpl.class);

    private static final String ENTITY_NAME = "borrowedBook";

    private final BorrowedBookRepository borrowedBookRepository;

    private final BookRepository bookRepository;

    private final BorrowedBookMapper borrowedBookMapper;

    public BorrowedBookServiceImpl(
        BorrowedBookRepository borrowedBookRepository,
        BookRepository bookRepository,
        BorrowedBookMapper borrowedBookMapper
    ) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.borrowedBookMapper = borrowedBookMapper;
    }

    @Override
    public BorrowedBookDTO save(BorrowedBookDTO borrowedBookDTO) {
        LOG.debug("Request to save BorrowedBook : {}", borrowedBookDTO);
        BorrowedBook borrowedBook = borrowedBookMapper.toEntity(borrowedBookDTO);
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        return borrowedBookMapper.toDto(borrowedBook);
    }

    @Override
    public BorrowedBookDTO update(BorrowedBookDTO borrowedBookDTO) {
        LOG.debug("Request to update BorrowedBook : {}", borrowedBookDTO);
        BorrowedBook borrowedBook = borrowedBookMapper.toEntity(borrowedBookDTO);
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        return borrowedBookMapper.toDto(borrowedBook);
    }

    @Override
    public Optional<BorrowedBookDTO> partialUpdate(BorrowedBookDTO borrowedBookDTO) {
        LOG.debug("Request to partially update BorrowedBook : {}", borrowedBookDTO);

        return borrowedBookRepository
            .findById(borrowedBookDTO.getId())
            .map(existingBorrowedBook -> {
                borrowedBookMapper.partialUpdate(existingBorrowedBook, borrowedBookDTO);

                return existingBorrowedBook;
            })
            .map(borrowedBookRepository::save)
            .map(borrowedBookMapper::toDto);
    }

    public Page<BorrowedBookDTO> findAllWithEagerRelationships(Pageable pageable) {
        return borrowedBookRepository.findAllWithEagerRelationships(pageable).map(borrowedBookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BorrowedBookDTO> findOne(Long id) {
        LOG.debug("Request to get BorrowedBook : {}", id);
        return borrowedBookRepository.findOneWithEagerRelationships(id).map(borrowedBookMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BorrowedBook : {}", id);
        borrowedBookRepository.deleteById(id);
    }

    @Override
    public BorrowedBookDTO borrowBook(BorrowedBookDTO borrowedBookDTO) {
        LOG.debug("Request to borrow Book : {}", borrowedBookDTO);

        // Find the actual Book entity by ID from the DTO
        Book book = bookRepository.findById(borrowedBookDTO.getBook().getId())
            .orElseThrow(() -> new BadRequestAlertException("Book not found", ENTITY_NAME, "booknotfound"));

        // Validate availability
        if (book.getCopies() == null || book.getCopies() <= 0) {
            throw new BadRequestAlertException("No copies available for this book", ENTITY_NAME, "nocopies");
        }

        // Decrement available copies
        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);

        // Auto-fill the bookIsbn from the Book entity
        borrowedBookDTO.setBookIsbn(book.getIsbn());

        // Create the BorrowedBook record
        BorrowedBook borrowedBook = borrowedBookMapper.toEntity(borrowedBookDTO);
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        return borrowedBookMapper.toDto(borrowedBook);
    }

    @Override
    public BorrowedBookDTO returnBook(Long id) {
        LOG.debug("Request to return BorrowedBook : {}", id);

        BorrowedBook borrowedBook = borrowedBookRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Borrowed book record not found", ENTITY_NAME, "idnotfound"));

        // Convert to DTO before deleting
        BorrowedBookDTO result = borrowedBookMapper.toDto(borrowedBook);

        // Increment available copies on the Book
        Book book = borrowedBook.getBook();
        book.setCopies((book.getCopies() != null ? book.getCopies() : 0) + 1);
        bookRepository.save(book);

        // Delete the BorrowedBook record
        borrowedBookRepository.deleteById(id);

        return result;
    }
}
