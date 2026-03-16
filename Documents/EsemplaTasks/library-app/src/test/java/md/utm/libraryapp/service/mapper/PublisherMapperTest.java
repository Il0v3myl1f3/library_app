package md.utm.libraryapp.service.mapper;

import static md.utm.libraryapp.domain.PublisherAsserts.*;
import static md.utm.libraryapp.domain.PublisherTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublisherMapperTest {

    private PublisherMapper publisherMapper;

    @BeforeEach
    void setUp() {
        publisherMapper = new PublisherMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublisherSample1();
        var actual = publisherMapper.toEntity(publisherMapper.toDto(expected));
        assertPublisherAllPropertiesEquals(expected, actual);
    }
}
