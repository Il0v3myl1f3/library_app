package md.utm.libraryapp.service.impl;

import java.util.Optional;
import md.utm.libraryapp.domain.BookAuthor;
import md.utm.libraryapp.repository.BookAuthorRepository;
import md.utm.libraryapp.service.BookAuthorService;
import md.utm.libraryapp.service.dto.BookAuthorDTO;
import md.utm.libraryapp.service.mapper.BookAuthorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link md.utm.libraryapp.domain.BookAuthor}.
 */
@Service
@Transactional
public class BookAuthorServiceImpl implements BookAuthorService {

    private static final Logger LOG = LoggerFactory.getLogger(BookAuthorServiceImpl.class);

    private final BookAuthorRepository bookAuthorRepository;

    private final BookAuthorMapper bookAuthorMapper;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, BookAuthorMapper bookAuthorMapper) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookAuthorMapper = bookAuthorMapper;
    }

    @Override
    public BookAuthorDTO save(BookAuthorDTO bookAuthorDTO) {
        LOG.debug("Request to save BookAuthor : {}", bookAuthorDTO);
        BookAuthor bookAuthor = bookAuthorMapper.toEntity(bookAuthorDTO);
        bookAuthor = bookAuthorRepository.save(bookAuthor);
        return bookAuthorMapper.toDto(bookAuthor);
    }

    @Override
    public BookAuthorDTO update(BookAuthorDTO bookAuthorDTO) {
        LOG.debug("Request to update BookAuthor : {}", bookAuthorDTO);
        BookAuthor bookAuthor = bookAuthorMapper.toEntity(bookAuthorDTO);
        bookAuthor = bookAuthorRepository.save(bookAuthor);
        return bookAuthorMapper.toDto(bookAuthor);
    }

    @Override
    public Optional<BookAuthorDTO> partialUpdate(BookAuthorDTO bookAuthorDTO) {
        LOG.debug("Request to partially update BookAuthor : {}", bookAuthorDTO);

        return bookAuthorRepository
            .findById(bookAuthorDTO.getId())
            .map(existingBookAuthor -> {
                bookAuthorMapper.partialUpdate(existingBookAuthor, bookAuthorDTO);

                return existingBookAuthor;
            })
            .map(bookAuthorRepository::save)
            .map(bookAuthorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookAuthorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BookAuthors");
        return bookAuthorRepository.findAll(pageable).map(bookAuthorMapper::toDto);
    }

    public Page<BookAuthorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bookAuthorRepository.findAllWithEagerRelationships(pageable).map(bookAuthorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookAuthorDTO> findOne(Long id) {
        LOG.debug("Request to get BookAuthor : {}", id);
        return bookAuthorRepository.findOneWithEagerRelationships(id).map(bookAuthorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BookAuthor : {}", id);
        bookAuthorRepository.deleteById(id);
    }
}
