package com.laofan.iantha.web.rest;

import static com.laofan.iantha.domain.ProductAsserts.*;
import static com.laofan.iantha.web.rest.TestUtil.createUpdateProxyForBean;
import static com.laofan.iantha.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laofan.iantha.IntegrationTest;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.domain.enumeration.ProdStatus;
import com.laofan.iantha.repository.ProductRepository;
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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRANS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String[] DEFAULT_IMAGES = new String[]{"AAAAAAAAAA"};
    private static final String[] UPDATED_IMAGES = new String[]{"BBBBBBBBBB"};

    private static final String[] DEFAULT_KEYWORDS = new String[]{"AAAAAAAAAA"};
    private static final String[] UPDATED_KEYWORDS = new String[]{"BBBBBBBBBB"};

    private static final String DEFAULT_METADATA = "AAAAAAAAAA";
    private static final String UPDATED_METADATA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GUIDE_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_GUIDE_PRICE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SHOW_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_SHOW_PRICE = new BigDecimal(1);

    private static final Integer DEFAULT_DISCOUNT = 1;
    private static final Integer UPDATED_DISCOUNT = 2;

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final Boolean DEFAULT_IS_PHYSICAL = false;
    private static final Boolean UPDATED_IS_PHYSICAL = true;

    private static final Boolean DEFAULT_IS_AVAILABLE = false;
    private static final Boolean UPDATED_IS_AVAILABLE = true;

    private static final Boolean DEFAULT_IS_FEATURED = false;
    private static final Boolean UPDATED_IS_FEATURED = true;

    private static final ProdStatus DEFAULT_STATUS = ProdStatus.PRE;
    private static final ProdStatus UPDATED_STATUS = ProdStatus.HOT;

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;

    private static final Integer DEFAULT_STOCK_COUNT = 1;
    private static final Integer UPDATED_STOCK_COUNT = 2;

    private static final Boolean DEFAULT_SHELVES_STATUS = false;
    private static final Boolean UPDATED_SHELVES_STATUS = true;

    private static final Instant DEFAULT_SHELVES_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHELVES_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    private Product insertedProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .title(DEFAULT_TITLE)
            .transCode(DEFAULT_TRANS_CODE)
            .description(DEFAULT_DESCRIPTION)
            .images(DEFAULT_IMAGES)
            .keywords(DEFAULT_KEYWORDS)
            .metadata(DEFAULT_METADATA)
            .guidePrice(DEFAULT_GUIDE_PRICE)
            .price(DEFAULT_PRICE)
            .showPrice(DEFAULT_SHOW_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .stock(DEFAULT_STOCK)
            .isPhysical(DEFAULT_IS_PHYSICAL)
            .isAvailable(DEFAULT_IS_AVAILABLE)
            .isFeatured(DEFAULT_IS_FEATURED)
            .status(DEFAULT_STATUS)
            .sellCount(DEFAULT_SELL_COUNT)
            .stockCount(DEFAULT_STOCK_COUNT)
            .shelvesStatus(DEFAULT_SHELVES_STATUS)
            .shelvesDate(DEFAULT_SHELVES_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .title(UPDATED_TITLE)
            .transCode(UPDATED_TRANS_CODE)
            .description(UPDATED_DESCRIPTION)
            .images(UPDATED_IMAGES)
            .keywords(UPDATED_KEYWORDS)
            .metadata(UPDATED_METADATA)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .price(UPDATED_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .discount(UPDATED_DISCOUNT)
            .stock(UPDATED_STOCK)
            .isPhysical(UPDATED_IS_PHYSICAL)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .isFeatured(UPDATED_IS_FEATURED)
            .status(UPDATED_STATUS)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .shelvesStatus(UPDATED_SHELVES_STATUS)
            .shelvesDate(UPDATED_SHELVES_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProduct != null) {
            productRepository.delete(insertedProduct);
            insertedProduct = null;
        }
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Product
        var returnedProduct = om.readValue(
            restProductMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Product.class
        );

        // Validate the Product in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductUpdatableFieldsEquals(returnedProduct, getPersistedProduct(returnedProduct));

        insertedProduct = returnedProduct;
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setTitle(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setTransCode(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGuidePriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setGuidePrice(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShowPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setShowPrice(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiscountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setDiscount(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStockIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setStock(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPhysicalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setIsPhysical(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAvailableIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setIsAvailable(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsFeaturedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setIsFeatured(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setCreatedAt(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setUpdatedAt(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].transCode").value(hasItem(DEFAULT_TRANS_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS)))
            .andExpect(jsonPath("$.[*].metadata").value(hasItem(DEFAULT_METADATA.toString())))
            .andExpect(jsonPath("$.[*].guidePrice").value(hasItem(sameNumber(DEFAULT_GUIDE_PRICE))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].showPrice").value(hasItem(sameNumber(DEFAULT_SHOW_PRICE))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].isPhysical").value(hasItem(DEFAULT_IS_PHYSICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isFeatured").value(hasItem(DEFAULT_IS_FEATURED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].stockCount").value(hasItem(DEFAULT_STOCK_COUNT)))
            .andExpect(jsonPath("$.[*].shelvesStatus").value(hasItem(DEFAULT_SHELVES_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].shelvesDate").value(hasItem(DEFAULT_SHELVES_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.transCode").value(DEFAULT_TRANS_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA.toString()))
            .andExpect(jsonPath("$.guidePrice").value(sameNumber(DEFAULT_GUIDE_PRICE)))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.showPrice").value(sameNumber(DEFAULT_SHOW_PRICE)))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.isPhysical").value(DEFAULT_IS_PHYSICAL.booleanValue()))
            .andExpect(jsonPath("$.isAvailable").value(DEFAULT_IS_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.isFeatured").value(DEFAULT_IS_FEATURED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.stockCount").value(DEFAULT_STOCK_COUNT))
            .andExpect(jsonPath("$.shelvesStatus").value(DEFAULT_SHELVES_STATUS.booleanValue()))
            .andExpect(jsonPath("$.shelvesDate").value(DEFAULT_SHELVES_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .title(UPDATED_TITLE)
            .transCode(UPDATED_TRANS_CODE)
            .description(UPDATED_DESCRIPTION)
            .images(UPDATED_IMAGES)
            .keywords(UPDATED_KEYWORDS)
            .metadata(UPDATED_METADATA)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .price(UPDATED_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .discount(UPDATED_DISCOUNT)
            .stock(UPDATED_STOCK)
            .isPhysical(UPDATED_IS_PHYSICAL)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .isFeatured(UPDATED_IS_FEATURED)
            .status(UPDATED_STATUS)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .shelvesStatus(UPDATED_SHELVES_STATUS)
            .shelvesDate(UPDATED_SHELVES_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductToMatchAllProperties(updatedProduct);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL_ID, product.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .images(UPDATED_IMAGES)
            .keywords(UPDATED_KEYWORDS)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .price(UPDATED_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .stock(UPDATED_STOCK)
            .isFeatured(UPDATED_IS_FEATURED)
            .sellCount(UPDATED_SELL_COUNT)
            .shelvesStatus(UPDATED_SHELVES_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProduct, product), getPersistedProduct(product));
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .title(UPDATED_TITLE)
            .transCode(UPDATED_TRANS_CODE)
            .description(UPDATED_DESCRIPTION)
            .images(UPDATED_IMAGES)
            .keywords(UPDATED_KEYWORDS)
            .metadata(UPDATED_METADATA)
            .guidePrice(UPDATED_GUIDE_PRICE)
            .price(UPDATED_PRICE)
            .showPrice(UPDATED_SHOW_PRICE)
            .discount(UPDATED_DISCOUNT)
            .stock(UPDATED_STOCK)
            .isPhysical(UPDATED_IS_PHYSICAL)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .isFeatured(UPDATED_IS_FEATURED)
            .status(UPDATED_STATUS)
            .sellCount(UPDATED_SELL_COUNT)
            .stockCount(UPDATED_STOCK_COUNT)
            .shelvesStatus(UPDATED_SHELVES_STATUS)
            .shelvesDate(UPDATED_SHELVES_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductUpdatableFieldsEquals(partialUpdatedProduct, getPersistedProduct(partialUpdatedProduct));
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, product.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productRepository.count();
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

    protected Product getPersistedProduct(Product product) {
        return productRepository.findById(product.getId()).orElseThrow();
    }

    protected void assertPersistedProductToMatchAllProperties(Product expectedProduct) {
        assertProductAllPropertiesEquals(expectedProduct, getPersistedProduct(expectedProduct));
    }

    protected void assertPersistedProductToMatchUpdatableProperties(Product expectedProduct) {
        assertProductAllUpdatablePropertiesEquals(expectedProduct, getPersistedProduct(expectedProduct));
    }
}
