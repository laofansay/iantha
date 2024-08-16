package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.PaymentProviderAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.PaymentProvider;
import com.laofan.iantha.repository.PaymentProviderRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PaymentProviderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentProviderResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/payment-providers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentProviderMockMvc;

    private PaymentProvider paymentProvider;

    private PaymentProvider insertedPaymentProvider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentProvider createEntity(EntityManager em) {
        PaymentProvider paymentProvider = new PaymentProvider()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .websiteUrl(DEFAULT_WEBSITE_URL)
            .isActive(DEFAULT_IS_ACTIVE);
        return paymentProvider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentProvider createUpdatedEntity(EntityManager em) {
        PaymentProvider paymentProvider = new PaymentProvider()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .isActive(UPDATED_IS_ACTIVE);
        return paymentProvider;
    }

    @BeforeEach
    public void initTest() {
        paymentProvider = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaymentProvider != null) {
            paymentProviderRepository.delete(insertedPaymentProvider);
            insertedPaymentProvider = null;
        }
    }

    @Test
    @Transactional
    void createPaymentProvider() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentProvider
        var returnedPaymentProvider = om.readValue(
            restPaymentProviderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentProvider)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentProvider.class
        );

        // Validate the PaymentProvider in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPaymentProviderUpdatableFieldsEquals(returnedPaymentProvider, getPersistedPaymentProvider(returnedPaymentProvider));

        insertedPaymentProvider = returnedPaymentProvider;
    }

    @Test
    @Transactional
    void createPaymentProviderWithExistingId() throws Exception {
        // Create the PaymentProvider with an existing ID
        paymentProvider.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentProvider)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentProvider.setTitle(null);

        // Create the PaymentProvider, which fails.

        restPaymentProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentProvider)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentProvider.setIsActive(null);

        // Create the PaymentProvider, which fails.

        restPaymentProviderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentProvider)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentProviders() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        // Get all the paymentProviderList
        restPaymentProviderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].websiteUrl").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getPaymentProvider() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        // Get the paymentProvider
        restPaymentProviderMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentProvider.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.websiteUrl").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentProvider() throws Exception {
        // Get the paymentProvider
        restPaymentProviderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentProvider() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentProvider
        PaymentProvider updatedPaymentProvider = paymentProviderRepository.findById(paymentProvider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentProvider are not directly saved in db
        em.detach(updatedPaymentProvider);
        updatedPaymentProvider
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .isActive(UPDATED_IS_ACTIVE);

        restPaymentProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentProvider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPaymentProvider))
            )
            .andExpect(status().isOk());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentProviderToMatchAllProperties(updatedPaymentProvider);
    }

    @Test
    @Transactional
    void putNonExistingPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentProvider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentProvider))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentProvider))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentProvider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentProviderWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentProvider using partial update
        PaymentProvider partialUpdatedPaymentProvider = new PaymentProvider();
        partialUpdatedPaymentProvider.setId(paymentProvider.getId());

        partialUpdatedPaymentProvider.description(UPDATED_DESCRIPTION).isActive(UPDATED_IS_ACTIVE);

        restPaymentProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentProvider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentProvider))
            )
            .andExpect(status().isOk());

        // Validate the PaymentProvider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentProviderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentProvider, paymentProvider),
            getPersistedPaymentProvider(paymentProvider)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentProviderWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentProvider using partial update
        PaymentProvider partialUpdatedPaymentProvider = new PaymentProvider();
        partialUpdatedPaymentProvider.setId(paymentProvider.getId());

        partialUpdatedPaymentProvider
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .isActive(UPDATED_IS_ACTIVE);

        restPaymentProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentProvider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentProvider))
            )
            .andExpect(status().isOk());

        // Validate the PaymentProvider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentProviderUpdatableFieldsEquals(
            partialUpdatedPaymentProvider,
            getPersistedPaymentProvider(partialUpdatedPaymentProvider)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentProvider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentProvider))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentProvider))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentProvider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentProvider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentProviderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paymentProvider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentProvider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentProvider() throws Exception {
        // Initialize the database
        insertedPaymentProvider = paymentProviderRepository.saveAndFlush(paymentProvider);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentProvider
        restPaymentProviderMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentProvider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentProviderRepository.count();
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

    protected PaymentProvider getPersistedPaymentProvider(PaymentProvider paymentProvider) {
        return paymentProviderRepository.findById(paymentProvider.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentProviderToMatchAllProperties(PaymentProvider expectedPaymentProvider) {
        assertPaymentProviderAllPropertiesEquals(expectedPaymentProvider, getPersistedPaymentProvider(expectedPaymentProvider));
    }

    protected void assertPersistedPaymentProviderToMatchUpdatableProperties(PaymentProvider expectedPaymentProvider) {
        assertPaymentProviderAllUpdatablePropertiesEquals(expectedPaymentProvider, getPersistedPaymentProvider(expectedPaymentProvider));
    }
}
