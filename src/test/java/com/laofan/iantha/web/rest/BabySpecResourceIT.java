package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.BabySpecAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static com.laofan.iantha.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.BabySpec;
import com.laofan.iantha.domain.enumeration.ProdStatus;
import com.laofan.iantha.repository.BabySpecRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BabySpecResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BabySpecResourceIT {

    private static final String DEFAULT_SPEC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SPEC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SPEC_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SPEC_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SPEC_QUANTITY = 1;
    private static final Integer UPDATED_SPEC_QUANTITY = 2;

    private static final BigDecimal DEFAULT_GUIDE_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_GUIDE_PRICE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SPEC_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_SPEC_PRICE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SHOW_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_SHOW_PRICE = new BigDecimal(1);

    private static final ProdStatus DEFAULT_SPEC_STATUS = ProdStatus.PRE;
    private static final ProdStatus UPDATED_SPEC_STATUS = ProdStatus.HOT;

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;

    private static final Integer DEFAULT_STOCK_COUNT = 1;
    private static final Integer UPDATED_STOCK_COUNT = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/baby-specs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BabySpecRepository babySpecRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBabySpecMockMvc;

    private BabySpec babySpec;

    private BabySpec insertedBabySpec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BabySpec createEntity(EntityManager em) {
        BabySpec babySpec = new BabySpec()
            .specCode(DEFAULT_SPEC_CODE)
            .specTitle(DEFAULT_SPEC_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .specQuantity(DEFAULT_SPEC_QUANTITY)
            .guidePrice(DEFAULT_GUIDE_PRICE)
            .specPrice(DEFAULT_SPEC_PRICE)
            .showPrice(DEFAULT_SHOW_PRICE)
            .specStatus(DEFAULT_SPEC_STATUS)
            .images(DEFAULT_IMAGES)
            .sellCount(DEFAULT_SELL_COUNT)
            .stockCount(DEFAULT_STOCK_COUNT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return babySpec;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BabySpec createUpdatedEntity(EntityManager em) {
        BabySpec babySpec = new BabySpec()
            .specCode(UPDATED_SPEC_CODE)
            .specTitle(UPDATED_SPEC_TITLE)
            .description(UPDATED_DESCRIPTION)
            .specQuantity(UPDATED_SPEC_QUANTITY)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .specPrice(UPDATED_SPEC_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .specStatus(UPDATED_SPEC_STATUS)
            .images(UPDATED_IMAGES)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return babySpec;
    }

    @BeforeEach
    public void initTest() {
        babySpec = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBabySpec != null) {
            babySpecRepository.delete(insertedBabySpec);
            insertedBabySpec = null;
        }
    }

    @Test
    @Transactional
    void createBabySpec() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BabySpec
        var returnedBabySpec = om.readValue(
            restBabySpecMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BabySpec.class
        );

        // Validate the BabySpec in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBabySpecUpdatableFieldsEquals(returnedBabySpec, getPersistedBabySpec(returnedBabySpec));

        insertedBabySpec = returnedBabySpec;
    }

    @Test
    @Transactional
    void createBabySpecWithExistingId() throws Exception {
        // Create the BabySpec with an existing ID
        babySpec.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSpecCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setSpecCode(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setSpecTitle(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setDescription(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setSpecQuantity(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGuidePriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setGuidePrice(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setSpecPrice(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShowPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setShowPrice(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setSpecStatus(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImagesIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setImages(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setCreatedAt(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babySpec.setUpdatedAt(null);

        // Create the BabySpec, which fails.

        restBabySpecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBabySpecs() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        // Get all the babySpecList
        restBabySpecMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(babySpec.getId().intValue())))
            .andExpect(jsonPath("$.[*].specCode").value(hasItem(DEFAULT_SPEC_CODE)))
            .andExpect(jsonPath("$.[*].specTitle").value(hasItem(DEFAULT_SPEC_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].specQuantity").value(hasItem(DEFAULT_SPEC_QUANTITY)))
            .andExpect(jsonPath("$.[*].guidePrice").value(hasItem(sameNumber(DEFAULT_GUIDE_PRICE))))
            .andExpect(jsonPath("$.[*].specPrice").value(hasItem(sameNumber(DEFAULT_SPEC_PRICE))))
            .andExpect(jsonPath("$.[*].showPrice").value(hasItem(sameNumber(DEFAULT_SHOW_PRICE))))
            .andExpect(jsonPath("$.[*].specStatus").value(hasItem(DEFAULT_SPEC_STATUS.toString())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].stockCount").value(hasItem(DEFAULT_STOCK_COUNT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getBabySpec() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        // Get the babySpec
        restBabySpecMockMvc
            .perform(get(ENTITY_API_URL_ID, babySpec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(babySpec.getId().intValue()))
            .andExpect(jsonPath("$.specCode").value(DEFAULT_SPEC_CODE))
            .andExpect(jsonPath("$.specTitle").value(DEFAULT_SPEC_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.specQuantity").value(DEFAULT_SPEC_QUANTITY))
            .andExpect(jsonPath("$.guidePrice").value(sameNumber(DEFAULT_GUIDE_PRICE)))
            .andExpect(jsonPath("$.specPrice").value(sameNumber(DEFAULT_SPEC_PRICE)))
            .andExpect(jsonPath("$.showPrice").value(sameNumber(DEFAULT_SHOW_PRICE)))
            .andExpect(jsonPath("$.specStatus").value(DEFAULT_SPEC_STATUS.toString()))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.stockCount").value(DEFAULT_STOCK_COUNT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBabySpec() throws Exception {
        // Get the babySpec
        restBabySpecMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBabySpec() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babySpec
        BabySpec updatedBabySpec = babySpecRepository.findById(babySpec.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBabySpec are not directly saved in db
        em.detach(updatedBabySpec);
        updatedBabySpec
            .specCode(UPDATED_SPEC_CODE)
            .specTitle(UPDATED_SPEC_TITLE)
            .description(UPDATED_DESCRIPTION)
            .specQuantity(UPDATED_SPEC_QUANTITY)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .specPrice(UPDATED_SPEC_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .specStatus(UPDATED_SPEC_STATUS)
            .images(UPDATED_IMAGES)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restBabySpecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBabySpec.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBabySpec))
            )
            .andExpect(status().isOk());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBabySpecToMatchAllProperties(updatedBabySpec);
    }

    @Test
    @Transactional
    void putNonExistingBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, babySpec.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(babySpec))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBabySpecWithPatch() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babySpec using partial update
        BabySpec partialUpdatedBabySpec = new BabySpec();
        partialUpdatedBabySpec.setId(babySpec.getId());

        partialUpdatedBabySpec
            .specTitle(UPDATED_SPEC_TITLE)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .specStatus(UPDATED_SPEC_STATUS)
            .images(UPDATED_IMAGES)
            .stockCount(UPDATED_STOCK_COUNT)
            .updatedAt(UPDATED_UPDATED_AT);

        restBabySpecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBabySpec.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBabySpec))
            )
            .andExpect(status().isOk());

        // Validate the BabySpec in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBabySpecUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBabySpec, babySpec), getPersistedBabySpec(babySpec));
    }

    @Test
    @Transactional
    void fullUpdateBabySpecWithPatch() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babySpec using partial update
        BabySpec partialUpdatedBabySpec = new BabySpec();
        partialUpdatedBabySpec.setId(babySpec.getId());

        partialUpdatedBabySpec
            .specCode(UPDATED_SPEC_CODE)
            .specTitle(UPDATED_SPEC_TITLE)
            .description(UPDATED_DESCRIPTION)
            .specQuantity(UPDATED_SPEC_QUANTITY)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .specPrice(UPDATED_SPEC_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .specStatus(UPDATED_SPEC_STATUS)
            .images(UPDATED_IMAGES)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restBabySpecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBabySpec.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBabySpec))
            )
            .andExpect(status().isOk());

        // Validate the BabySpec in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBabySpecUpdatableFieldsEquals(partialUpdatedBabySpec, getPersistedBabySpec(partialUpdatedBabySpec));
    }

    @Test
    @Transactional
    void patchNonExistingBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, babySpec.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(babySpec))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(babySpec))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBabySpec() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babySpec.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabySpecMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(babySpec)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BabySpec in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBabySpec() throws Exception {
        // Initialize the database
        insertedBabySpec = babySpecRepository.saveAndFlush(babySpec);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the babySpec
        restBabySpecMockMvc
            .perform(delete(ENTITY_API_URL_ID, babySpec.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return babySpecRepository.count();
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

    protected BabySpec getPersistedBabySpec(BabySpec babySpec) {
        return babySpecRepository.findById(babySpec.getId()).orElseThrow();
    }

    protected void assertPersistedBabySpecToMatchAllProperties(BabySpec expectedBabySpec) {
        assertBabySpecAllPropertiesEquals(expectedBabySpec, getPersistedBabySpec(expectedBabySpec));
    }

    protected void assertPersistedBabySpecToMatchUpdatableProperties(BabySpec expectedBabySpec) {
        assertBabySpecAllUpdatablePropertiesEquals(expectedBabySpec, getPersistedBabySpec(expectedBabySpec));
    }
}
