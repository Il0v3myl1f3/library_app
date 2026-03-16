package md.utm.libraryapp.service.impl;

import java.util.Optional;
import md.utm.libraryapp.domain.BorrowedBook;
import md.utm.libraryapp.repository.BorrowedBookRepository;
import md.utm.libraryapp.service.BorrowedBookService;
import md.utm.libraryapp.service.dto.BorrowedBookDTO;
import md.utm.libraryapp.service.mapper.BorrowedBookMapper;
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

    private final BorrowedBookRepository borrowedBookRepository;

    private final BorrowedBookMapper borrowedBookMapper;

    public BorrowedBookServiceImpl(BorrowedBookRepository borrowedBookRepository, BorrowedBookMapper borrowedBookMapper) {
        this.borrowedBookRepository = borrowedBookRepository;
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
}
