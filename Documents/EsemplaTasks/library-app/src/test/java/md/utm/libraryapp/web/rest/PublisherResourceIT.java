package md.utm.libraryapp.web.rest;

import static md.utm.libraryapp.domain.PublisherAsserts.*;
import static md.utm.libraryapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import md.utm.libraryapp.IntegrationTest;
import md.utm.libraryapp.domain.Publisher;
import md.utm.libraryapp.repository.PublisherRepository;
import md.utm.libraryapp.service.dto.PublisherDTO;
import md.utm.libraryapp.service.mapper.PublisherMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PublisherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublisherResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/publishers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherMapper publisherMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublisherMockMvc;

    private Publisher publisher;

    private Publisher insertedPublisher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publisher createEntity() {
        return new Publisher().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publisher createUpdatedEntity() {
        return new Publisher().name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        publisher = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublisher != null) {
            publisherRepository.delete(insertedPublisher);
            insertedPublisher = null;
        }
    }

    @Test
    @Transactional
    void createPublisher() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);
        var returnedPublisherDTO = om.readValue(
            restPublisherMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publisherDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublisherDTO.class
        );

        // Validate the Publisher in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublisher = publisherMapper.toEntity(returnedPublisherDTO);
        assertPublisherUpdatableFieldsEquals(returnedPublisher, getPersistedPublisher(returnedPublisher));

        insertedPublisher = returnedPublisher;
    }

    @Test
    @Transactional
    void createPublisherWithExistingId() throws Exception {
        // Create the Publisher with an existing ID
        publisher.setId(1L);
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublisherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publisherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        publisher.setName(null);

        // Create the Publisher, which fails.
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        restPublisherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publisherDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPublishers() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publisher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPublisher() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get the publisher
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL_ID, publisher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publisher.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getPublishersByIdFiltering() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        Long id = publisher.getId();

        defaultPublisherFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPublisherFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPublisherFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPublishersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList where name equals to
        defaultPublisherFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPublishersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList where name in
        defaultPublisherFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPublishersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList where name is not null
        defaultPublisherFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllPublishersByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList where name contains
        defaultPublisherFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPublishersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList where name does not contain
        defaultPublisherFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    private void defaultPublisherFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPublisherShouldBeFound(shouldBeFound);
        defaultPublisherShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublisherShouldBeFound(String filter) throws Exception {
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publisher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublisherShouldNotBeFound(String filter) throws Exception {
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPublisher() throws Exception {
        // Get the publisher
        restPublisherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublisher() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publisher
        Publisher updatedPublisher = publisherRepository.findById(publisher.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublisher are not directly saved in db
        em.detach(updatedPublisher);
        updatedPublisher.name(UPDATED_NAME);
        PublisherDTO publisherDTO = publisherMapper.toDto(updatedPublisher);

        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publisherDTO))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublisherToMatchAllProperties(updatedPublisher);
    }

    @Test
    @Transactional
    void putNonExistingPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publisherDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublisherWithPatch() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publisher using partial update
        Publisher partialUpdatedPublisher = new Publisher();
        partialUpdatedPublisher.setId(publisher.getId());

        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublisher.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublisher))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublisherUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublisher, publisher),
            getPersistedPublisher(publisher)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublisherWithPatch() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publisher using partial update
        Publisher partialUpdatedPublisher = new Publisher();
        partialUpdatedPublisher.setId(publisher.getId());

        partialUpdatedPublisher.name(UPDATED_NAME);

        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublisher.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublisher))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublisherUpdatableFieldsEquals(partialUpdatedPublisher, getPersistedPublisher(partialUpdatedPublisher));
    }

    @Test
    @Transactional
    void patchNonExistingPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublisher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publisher.setId(longCount.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publisherDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publisher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublisher() throws Exception {
        // Initialize the database
        insertedPublisher = publisherRepository.saveAndFlush(publisher);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publisher
        restPublisherMockMvc
            .perform(delete(ENTITY_API_URL_ID, publisher.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publisherRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Publisher getPersistedPublisher(Publisher publisher) {
        return publisherRepository.findById(publisher.getId()).orElseThrow();
    }

    protected void assertPersistedPublisherToMatchAllProperties(Publisher expectedPublisher) {
        assertPublisherAllPropertiesEquals(expectedPublisher, getPersistedPublisher(expectedPublisher));
    }

    protected void assertPersistedPublisherToMatchUpdatableProperties(Publisher expectedPublisher) {
        assertPublisherAllUpdatablePropertiesEquals(expectedPublisher, getPersistedPublisher(expectedPublisher));
    }
}
