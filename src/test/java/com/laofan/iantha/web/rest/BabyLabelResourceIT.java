package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.BabyLabelAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.BabyLabel;
import com.laofan.iantha.domain.enumeration.LabelCate;
import com.laofan.iantha.repository.BabyLabelRepository;
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
 * Integration tests for the {@link BabyLabelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BabyLabelResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LabelCate DEFAULT_LABEL_CATE = LabelCate.SEARCH;
    private static final LabelCate UPDATED_LABEL_CATE = LabelCate.STAT;

    private static final String DEFAULT_LABEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_ATTR = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_ATTR = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFY = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFY = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_README = "AAAAAAAAAA";
    private static final String UPDATED_RULE_README = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_RULE_EXPRESSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/baby-labels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BabyLabelRepository babyLabelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBabyLabelMockMvc;

    private BabyLabel babyLabel;

    private BabyLabel insertedBabyLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BabyLabel createEntity(EntityManager em) {
        BabyLabel babyLabel = new BabyLabel()
            .title(DEFAULT_TITLE)
            .labelCate(DEFAULT_LABEL_CATE)
            .labelCode(DEFAULT_LABEL_CODE)
            .labelAttr(DEFAULT_LABEL_ATTR)
            .identify(DEFAULT_IDENTIFY)
            .ruleReadme(DEFAULT_RULE_README)
            .ruleExpression(DEFAULT_RULE_EXPRESSION);
        return babyLabel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BabyLabel createUpdatedEntity(EntityManager em) {
        BabyLabel babyLabel = new BabyLabel()
            .title(UPDATED_TITLE)
            .labelCate(UPDATED_LABEL_CATE)
            .labelCode(UPDATED_LABEL_CODE)
            .labelAttr(UPDATED_LABEL_ATTR)
            .identify(UPDATED_IDENTIFY)
            .ruleReadme(UPDATED_RULE_README)
            .ruleExpression(UPDATED_RULE_EXPRESSION);
        return babyLabel;
    }

    @BeforeEach
    public void initTest() {
        babyLabel = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBabyLabel != null) {
            babyLabelRepository.delete(insertedBabyLabel);
            insertedBabyLabel = null;
        }
    }

    @Test
    @Transactional
    void createBabyLabel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BabyLabel
        var returnedBabyLabel = om.readValue(
            restBabyLabelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BabyLabel.class
        );

        // Validate the BabyLabel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBabyLabelUpdatableFieldsEquals(returnedBabyLabel, getPersistedBabyLabel(returnedBabyLabel));

        insertedBabyLabel = returnedBabyLabel;
    }

    @Test
    @Transactional
    void createBabyLabelWithExistingId() throws Exception {
        // Create the BabyLabel with an existing ID
        babyLabel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babyLabel.setTitle(null);

        // Create the BabyLabel, which fails.

        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLabelCateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babyLabel.setLabelCate(null);

        // Create the BabyLabel, which fails.

        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLabelCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babyLabel.setLabelCode(null);

        // Create the BabyLabel, which fails.

        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLabelAttrIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babyLabel.setLabelAttr(null);

        // Create the BabyLabel, which fails.

        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentifyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        babyLabel.setIdentify(null);

        // Create the BabyLabel, which fails.

        restBabyLabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBabyLabels() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        // Get all the babyLabelList
        restBabyLabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(babyLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].labelCate").value(hasItem(DEFAULT_LABEL_CATE.toString())))
            .andExpect(jsonPath("$.[*].labelCode").value(hasItem(DEFAULT_LABEL_CODE)))
            .andExpect(jsonPath("$.[*].labelAttr").value(hasItem(DEFAULT_LABEL_ATTR)))
            .andExpect(jsonPath("$.[*].identify").value(hasItem(DEFAULT_IDENTIFY)))
            .andExpect(jsonPath("$.[*].ruleReadme").value(hasItem(DEFAULT_RULE_README.toString())))
            .andExpect(jsonPath("$.[*].ruleExpression").value(hasItem(DEFAULT_RULE_EXPRESSION.toString())));
    }

    @Test
    @Transactional
    void getBabyLabel() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        // Get the babyLabel
        restBabyLabelMockMvc
            .perform(get(ENTITY_API_URL_ID, babyLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(babyLabel.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.labelCate").value(DEFAULT_LABEL_CATE.toString()))
            .andExpect(jsonPath("$.labelCode").value(DEFAULT_LABEL_CODE))
            .andExpect(jsonPath("$.labelAttr").value(DEFAULT_LABEL_ATTR))
            .andExpect(jsonPath("$.identify").value(DEFAULT_IDENTIFY))
            .andExpect(jsonPath("$.ruleReadme").value(DEFAULT_RULE_README.toString()))
            .andExpect(jsonPath("$.ruleExpression").value(DEFAULT_RULE_EXPRESSION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBabyLabel() throws Exception {
        // Get the babyLabel
        restBabyLabelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBabyLabel() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babyLabel
        BabyLabel updatedBabyLabel = babyLabelRepository.findById(babyLabel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBabyLabel are not directly saved in db
        em.detach(updatedBabyLabel);
        updatedBabyLabel
            .title(UPDATED_TITLE)
            .labelCate(UPDATED_LABEL_CATE)
            .labelCode(UPDATED_LABEL_CODE)
            .labelAttr(UPDATED_LABEL_ATTR)
            .identify(UPDATED_IDENTIFY)
            .ruleReadme(UPDATED_RULE_README)
            .ruleExpression(UPDATED_RULE_EXPRESSION);

        restBabyLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBabyLabel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBabyLabel))
            )
            .andExpect(status().isOk());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBabyLabelToMatchAllProperties(updatedBabyLabel);
    }

    @Test
    @Transactional
    void putNonExistingBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, babyLabel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(babyLabel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBabyLabelWithPatch() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babyLabel using partial update
        BabyLabel partialUpdatedBabyLabel = new BabyLabel();
        partialUpdatedBabyLabel.setId(babyLabel.getId());

        partialUpdatedBabyLabel
            .labelCode(UPDATED_LABEL_CODE)
            .identify(UPDATED_IDENTIFY)
            .ruleReadme(UPDATED_RULE_README)
            .ruleExpression(UPDATED_RULE_EXPRESSION);

        restBabyLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBabyLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBabyLabel))
            )
            .andExpect(status().isOk());

        // Validate the BabyLabel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBabyLabelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBabyLabel, babyLabel),
            getPersistedBabyLabel(babyLabel)
        );
    }

    @Test
    @Transactional
    void fullUpdateBabyLabelWithPatch() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the babyLabel using partial update
        BabyLabel partialUpdatedBabyLabel = new BabyLabel();
        partialUpdatedBabyLabel.setId(babyLabel.getId());

        partialUpdatedBabyLabel
            .title(UPDATED_TITLE)
            .labelCate(UPDATED_LABEL_CATE)
            .labelCode(UPDATED_LABEL_CODE)
            .labelAttr(UPDATED_LABEL_ATTR)
            .identify(UPDATED_IDENTIFY)
            .ruleReadme(UPDATED_RULE_README)
            .ruleExpression(UPDATED_RULE_EXPRESSION);

        restBabyLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBabyLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBabyLabel))
            )
            .andExpect(status().isOk());

        // Validate the BabyLabel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBabyLabelUpdatableFieldsEquals(partialUpdatedBabyLabel, getPersistedBabyLabel(partialUpdatedBabyLabel));
    }

    @Test
    @Transactional
    void patchNonExistingBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, babyLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(babyLabel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(babyLabel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBabyLabel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        babyLabel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBabyLabelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(babyLabel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BabyLabel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBabyLabel() throws Exception {
        // Initialize the database
        insertedBabyLabel = babyLabelRepository.saveAndFlush(babyLabel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the babyLabel
        restBabyLabelMockMvc
            .perform(delete(ENTITY_API_URL_ID, babyLabel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return babyLabelRepository.count();
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

    protected BabyLabel getPersistedBabyLabel(BabyLabel babyLabel) {
        return babyLabelRepository.findById(babyLabel.getId()).orElseThrow();
    }

    protected void assertPersistedBabyLabelToMatchAllProperties(BabyLabel expectedBabyLabel) {
        assertBabyLabelAllPropertiesEquals(expectedBabyLabel, getPersistedBabyLabel(expectedBabyLabel));
    }

    protected void assertPersistedBabyLabelToMatchUpdatableProperties(BabyLabel expectedBabyLabel) {
        assertBabyLabelAllUpdatablePropertiesEquals(expectedBabyLabel, getPersistedBabyLabel(expectedBabyLabel));
    }
}
