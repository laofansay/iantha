package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.RefundAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.Refund;
import com.laofan.iantha.repository.RefundRepository;
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
 * Integration tests for the {@link RefundResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefundResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/refunds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundMockMvc;

    private Refund refund;

    private Refund insertedRefund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createEntity(EntityManager em) {
        Refund refund = new Refund()
            .amount(DEFAULT_AMOUNT)
            .reason(DEFAULT_REASON)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return refund;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createUpdatedEntity(EntityManager em) {
        Refund refund = new Refund()
            .amount(UPDATED_AMOUNT)
            .reason(UPDATED_REASON)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return refund;
    }

    @BeforeEach
    public void initTest() {
        refund = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRefund != null) {
            refundRepository.delete(insertedRefund);
            insertedRefund = null;
        }
    }

    @Test
    @Transactional
    void createRefund() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Refund
        var returnedRefund = om.readValue(
            restRefundMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Refund.class
        );

        // Validate the Refund in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRefundUpdatableFieldsEquals(returnedRefund, getPersistedRefund(returnedRefund));

        insertedRefund = returnedRefund;
    }

    @Test
    @Transactional
    void createRefundWithExistingId() throws Exception {
        // Create the Refund with an existing ID
        refund.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        refund.setAmount(null);

        // Create the Refund, which fails.

        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReasonIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        refund.setReason(null);

        // Create the Refund, which fails.

        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        refund.setCreatedAt(null);

        // Create the Refund, which fails.

        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        refund.setUpdatedAt(null);

        // Create the Refund, which fails.

        restRefundMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRefunds() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        // Get all the refundList
        restRefundMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getRefund() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        // Get the refund
        restRefundMockMvc
            .perform(get(ENTITY_API_URL_ID, refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refund.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRefund() throws Exception {
        // Get the refund
        restRefundMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRefund() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the refund
        Refund updatedRefund = refundRepository.findById(refund.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRefund are not directly saved in db
        em.detach(updatedRefund);
        updatedRefund.amount(UPDATED_AMOUNT).reason(UPDATED_REASON).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRefund.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRefundToMatchAllProperties(updatedRefund);
    }

    @Test
    @Transactional
    void putNonExistingRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(put(ENTITY_API_URL_ID, refund.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund.amount(UPDATED_AMOUNT).reason(UPDATED_REASON).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRefundUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRefund, refund), getPersistedRefund(refund));
    }

    @Test
    @Transactional
    void fullUpdateRefundWithPatch() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the refund using partial update
        Refund partialUpdatedRefund = new Refund();
        partialUpdatedRefund.setId(refund.getId());

        partialUpdatedRefund.amount(UPDATED_AMOUNT).reason(UPDATED_REASON).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefund.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRefund))
            )
            .andExpect(status().isOk());

        // Validate the Refund in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRefundUpdatableFieldsEquals(partialUpdatedRefund, getPersistedRefund(partialUpdatedRefund));
    }

    @Test
    @Transactional
    void patchNonExistingRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refund.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(refund))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefund() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        refund.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefundMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(refund)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refund in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefund() throws Exception {
        // Initialize the database
        insertedRefund = refundRepository.saveAndFlush(refund);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the refund
        restRefundMockMvc
            .perform(delete(ENTITY_API_URL_ID, refund.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return refundRepository.count();
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

    protected Refund getPersistedRefund(Refund refund) {
        return refundRepository.findById(refund.getId()).orElseThrow();
    }

    protected void assertPersistedRefundToMatchAllProperties(Refund expectedRefund) {
        assertRefundAllPropertiesEquals(expectedRefund, getPersistedRefund(expectedRefund));
    }

    protected void assertPersistedRefundToMatchUpdatableProperties(Refund expectedRefund) {
        assertRefundAllUpdatablePropertiesEquals(expectedRefund, getPersistedRefund(expectedRefund));
    }
}
