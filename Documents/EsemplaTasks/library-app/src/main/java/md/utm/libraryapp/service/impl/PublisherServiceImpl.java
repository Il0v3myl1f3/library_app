package md.utm.libraryapp.service.impl;

import java.util.Optional;
import md.utm.libraryapp.domain.Publisher;
import md.utm.libraryapp.repository.PublisherRepository;
import md.utm.libraryapp.service.PublisherService;
import md.utm.libraryapp.service.dto.PublisherDTO;
import md.utm.libraryapp.service.mapper.PublisherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link md.utm.libraryapp.domain.Publisher}.
 */
@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherServiceImpl.class);

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    @Override
    public PublisherDTO save(PublisherDTO publisherDTO) {
        LOG.debug("Request to save Publisher : {}", publisherDTO);
        Publisher publisher = publisherMapper.toEntity(publisherDTO);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(publisher);
    }

    @Override
    public PublisherDTO update(PublisherDTO publisherDTO) {
        LOG.debug("Request to update Publisher : {}", publisherDTO);
        Publisher publisher = publisherMapper.toEntity(publisherDTO);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(publisher);
    }

    @Override
    public Optional<PublisherDTO> partialUpdate(PublisherDTO publisherDTO) {
        LOG.debug("Request to partially update Publisher : {}", publisherDTO);

        return publisherRepository
            .findById(publisherDTO.getId())
            .map(existingPublisher -> {
                publisherMapper.partialUpdate(existingPublisher, publisherDTO);

                return existingPublisher;
            })
            .map(publisherRepository::save)
            .map(publisherMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublisherDTO> findOne(Long id) {
        LOG.debug("Request to get Publisher : {}", id);
        return publisherRepository.findById(id).map(publisherMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Publisher : {}", id);
        publisherRepository.deleteById(id);
    }
}
