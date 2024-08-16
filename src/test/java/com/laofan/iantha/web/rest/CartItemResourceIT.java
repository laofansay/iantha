package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.CartItemAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.CartItem;
import com.laofan.iantha.repository.CartItemRepository;
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
 * Integration tests for the {@link CartItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CartItemResourceIT {

    private static final String DEFAULT_CART_ID = "AAAAAAAAAA";
    private static final String UPDATED_CART_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final String ENTITY_API_URL = "/api/cart-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartItemMockMvc;

    private CartItem cartItem;

    private CartItem insertedCartItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartItem createEntity(EntityManager em) {
        CartItem cartItem = new CartItem().cartId(DEFAULT_CART_ID).productId(DEFAULT_PRODUCT_ID).count(DEFAULT_COUNT);
        return cartItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartItem createUpdatedEntity(EntityManager em) {
        CartItem cartItem = new CartItem().cartId(UPDATED_CART_ID).productId(UPDATED_PRODUCT_ID).count(UPDATED_COUNT);
        return cartItem;
    }

    @BeforeEach
    public void initTest() {
        cartItem = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCartItem != null) {
            cartItemRepository.delete(insertedCartItem);
            insertedCartItem = null;
        }
    }

    @Test
    @Transactional
    void createCartItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CartItem
        var returnedCartItem = om.readValue(
            restCartItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CartItem.class
        );

        // Validate the CartItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCartItemUpdatableFieldsEquals(returnedCartItem, getPersistedCartItem(returnedCartItem));

        insertedCartItem = returnedCartItem;
    }

    @Test
    @Transactional
    void createCartItemWithExistingId() throws Exception {
        // Create the CartItem with an existing ID
        cartItem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCartIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cartItem.setCartId(null);

        // Create the CartItem, which fails.

        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProductIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cartItem.setProductId(null);

        // Create the CartItem, which fails.

        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cartItem.setCount(null);

        // Create the CartItem, which fails.

        restCartItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCartItems() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList
        restCartItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    void getCartItem() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        // Get the cartItem
        restCartItemMockMvc
            .perform(get(ENTITY_API_URL_ID, cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartItem.getId().intValue()))
            .andExpect(jsonPath("$.cartId").value(DEFAULT_CART_ID))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    void getNonExistingCartItem() throws Exception {
        // Get the cartItem
        restCartItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCartItem() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cartItem
        CartItem updatedCartItem = cartItemRepository.findById(cartItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCartItem are not directly saved in db
        em.detach(updatedCartItem);
        updatedCartItem.cartId(UPDATED_CART_ID).productId(UPDATED_PRODUCT_ID).count(UPDATED_COUNT);

        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCartItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCartItem))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCartItemToMatchAllProperties(updatedCartItem);
    }

    @Test
    @Transactional
    void putNonExistingCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartItem.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cartItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartItemWithPatch() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cartItem using partial update
        CartItem partialUpdatedCartItem = new CartItem();
        partialUpdatedCartItem.setId(cartItem.getId());

        partialUpdatedCartItem.cartId(UPDATED_CART_ID).productId(UPDATED_PRODUCT_ID).count(UPDATED_COUNT);

        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCartItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCartItem))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCartItemUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCartItem, cartItem), getPersistedCartItem(cartItem));
    }

    @Test
    @Transactional
    void fullUpdateCartItemWithPatch() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cartItem using partial update
        CartItem partialUpdatedCartItem = new CartItem();
        partialUpdatedCartItem.setId(cartItem.getId());

        partialUpdatedCartItem.cartId(UPDATED_CART_ID).productId(UPDATED_PRODUCT_ID).count(UPDATED_COUNT);

        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCartItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCartItem))
            )
            .andExpect(status().isOk());

        // Validate the CartItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCartItemUpdatableFieldsEquals(partialUpdatedCartItem, getPersistedCartItem(partialUpdatedCartItem));
    }

    @Test
    @Transactional
    void patchNonExistingCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cartItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cartItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cartItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCartItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cartItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cartItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CartItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCartItem() throws Exception {
        // Initialize the database
        insertedCartItem = cartItemRepository.saveAndFlush(cartItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cartItem
        restCartItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, cartItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cartItemRepository.count();
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

    protected CartItem getPersistedCartItem(CartItem cartItem) {
        return cartItemRepository.findById(cartItem.getId()).orElseThrow();
    }

    protected void assertPersistedCartItemToMatchAllProperties(CartItem expectedCartItem) {
        assertCartItemAllPropertiesEquals(expectedCartItem, getPersistedCartItem(expectedCartItem));
    }

    protected void assertPersistedCartItemToMatchUpdatableProperties(CartItem expectedCartItem) {
        assertCartItemAllUpdatablePropertiesEquals(expectedCartItem, getPersistedCartItem(expectedCartItem));
    }
}
