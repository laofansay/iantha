package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.DiscountCodeAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.DiscountCode;
import com.laofan.iantha.repository.DiscountCodeRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link DiscountCodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiscountCodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERCENT = 1;
    private static final Integer UPDATED_PERCENT = 2;

    private static final Float DEFAULT_MAX_DISCOUNT_AMOUNT = 1F;
    private static final Float UPDATED_MAX_DISCOUNT_AMOUNT = 2F;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/discount-codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountCodeMockMvc;

    private DiscountCode discountCode;

    private DiscountCode insertedDiscountCode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountCode createEntity(EntityManager em) {
        DiscountCode discountCode = new DiscountCode()
            .code(DEFAULT_CODE)
            .stock(DEFAULT_STOCK)
            .description(DEFAULT_DESCRIPTION)
            .percent(DEFAULT_PERCENT)
            .maxDiscountAmount(DEFAULT_MAX_DISCOUNT_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .createdAt(DEFAULT_CREATED_AT);
        return discountCode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountCode createUpdatedEntity(EntityManager em) {
        DiscountCode discountCode = new DiscountCode()
            .code(UPDATED_CODE)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .percent(UPDATED_PERCENT)
            .maxDiscountAmount(UPDATED_MAX_DISCOUNT_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT);
        return discountCode;
    }

    @BeforeEach
    public void initTest() {
        discountCode = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDiscountCode != null) {
            discountCodeRepository.delete(insertedDiscountCode);
            insertedDiscountCode = null;
        }
    }

    @Test
    @Transactional
    void createDiscountCode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DiscountCode
        var returnedDiscountCode = om.readValue(
            restDiscountCodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DiscountCode.class
        );

        // Validate the DiscountCode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDiscountCodeUpdatableFieldsEquals(returnedDiscountCode, getPersistedDiscountCode(returnedDiscountCode));

        insertedDiscountCode = returnedDiscountCode;
    }

    @Test
    @Transactional
    void createDiscountCodeWithExistingId() throws Exception {
        // Create the DiscountCode with an existing ID
        discountCode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setCode(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStockIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setStock(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPercentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setPercent(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaxDiscountAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setMaxDiscountAmount(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setStartDate(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setEndDate(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        discountCode.setCreatedAt(null);

        // Create the DiscountCode, which fails.

        restDiscountCodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiscountCodes() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        // Get all the discountCodeList
        restDiscountCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.[*].maxDiscountAmount").value(hasItem(DEFAULT_MAX_DISCOUNT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getDiscountCode() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        // Get the discountCode
        restDiscountCodeMockMvc
            .perform(get(ENTITY_API_URL_ID, discountCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discountCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT))
            .andExpect(jsonPath("$.maxDiscountAmount").value(DEFAULT_MAX_DISCOUNT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDiscountCode() throws Exception {
        // Get the discountCode
        restDiscountCodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiscountCode() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the discountCode
        DiscountCode updatedDiscountCode = discountCodeRepository.findById(discountCode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiscountCode are not directly saved in db
        em.detach(updatedDiscountCode);
        updatedDiscountCode
            .code(UPDATED_CODE)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .percent(UPDATED_PERCENT)
            .maxDiscountAmount(UPDATED_MAX_DISCOUNT_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restDiscountCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiscountCode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDiscountCode))
            )
            .andExpect(status().isOk());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDiscountCodeToMatchAllProperties(updatedDiscountCode);
    }

    @Test
    @Transactional
    void putNonExistingDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, discountCode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(discountCode))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(discountCode))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiscountCodeWithPatch() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the discountCode using partial update
        DiscountCode partialUpdatedDiscountCode = new DiscountCode();
        partialUpdatedDiscountCode.setId(discountCode.getId());

        partialUpdatedDiscountCode.stock(UPDATED_STOCK).percent(UPDATED_PERCENT).endDate(UPDATED_END_DATE);

        restDiscountCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscountCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiscountCode))
            )
            .andExpect(status().isOk());

        // Validate the DiscountCode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiscountCodeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDiscountCode, discountCode),
            getPersistedDiscountCode(discountCode)
        );
    }

    @Test
    @Transactional
    void fullUpdateDiscountCodeWithPatch() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the discountCode using partial update
        DiscountCode partialUpdatedDiscountCode = new DiscountCode();
        partialUpdatedDiscountCode.setId(discountCode.getId());

        partialUpdatedDiscountCode
            .code(UPDATED_CODE)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .percent(UPDATED_PERCENT)
            .maxDiscountAmount(UPDATED_MAX_DISCOUNT_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restDiscountCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscountCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiscountCode))
            )
            .andExpect(status().isOk());

        // Validate the DiscountCode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiscountCodeUpdatableFieldsEquals(partialUpdatedDiscountCode, getPersistedDiscountCode(partialUpdatedDiscountCode));
    }

    @Test
    @Transactional
    void patchNonExistingDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, discountCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(discountCode))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(discountCode))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiscountCode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        discountCode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(discountCode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiscountCode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiscountCode() throws Exception {
        // Initialize the database
        insertedDiscountCode = discountCodeRepository.saveAndFlush(discountCode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the discountCode
        restDiscountCodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, discountCode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return discountCodeRepository.count();
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

    protected DiscountCode getPersistedDiscountCode(DiscountCode discountCode) {
        return discountCodeRepository.findById(discountCode.getId()).orElseThrow();
    }

    protected void assertPersistedDiscountCodeToMatchAllProperties(DiscountCode expectedDiscountCode) {
        assertDiscountCodeAllPropertiesEquals(expectedDiscountCode, getPersistedDiscountCode(expectedDiscountCode));
    }

    protected void assertPersistedDiscountCodeToMatchUpdatableProperties(DiscountCode expectedDiscountCode) {
        assertDiscountCodeAllUpdatablePropertiesEquals(expectedDiscountCode, getPersistedDiscountCode(expectedDiscountCode));
    }
}
